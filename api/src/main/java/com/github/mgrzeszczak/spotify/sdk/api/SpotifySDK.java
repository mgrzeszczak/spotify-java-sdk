package com.github.mgrzeszczak.spotify.sdk.api;

import java.lang.annotation.Annotation;
import java.util.Objects;

import org.reactivestreams.Publisher;

import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.ErrorHolder;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.AuthError;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public final class SpotifySDK {

    private final String clientId;
    private final String clientSecret;
    private final AlbumService albumService;
    private final AuthorizationService authorizationService;
    private final RxJavaExceptionConverter apiExceptionConverter;
    private final RxJavaExceptionConverter authExceptionConverter;

    private SpotifySDK(String clientId,
                       String clientSecret,
                       AlbumService albumService,
                       AuthorizationService authorizationService,
                       RxJavaExceptionConverter apiExceptionConverter,
                       RxJavaExceptionConverter authExceptionConverter) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.albumService = albumService;
        this.authorizationService = authorizationService;
        this.apiExceptionConverter = apiExceptionConverter;
        this.authExceptionConverter = authExceptionConverter;
    }

    public Single<TokenData> getToken(String authorizationCode, String redirectUri) {
        Objects.requireNonNull(authorizationCode);
        Objects.requireNonNull(redirectUri);
        return authorizationService.getToken(
                AuthorizationService.URL,
                clientId,
                clientSecret,
                AuthorizationService.CODE_GRANT_TYPE,
                authorizationCode,
                redirectUri
        ).onErrorResumeNext(authExceptionConverter::convertSingle);
    }

    public Single<TokenData> refreshToken(String refreshToken) {
        Objects.requireNonNull(refreshToken);
        return authorizationService.refreshToken(
                AuthorizationService.URL,
                clientId,
                clientSecret,
                AuthorizationService.REFRESH_TOKEN_GRANT_TYPE,
                refreshToken
        ).onErrorResumeNext(authExceptionConverter::convertSingle);
    }

    public Single<Album> getAlbum(String authorization, String id) {
        return albumService.getAlbum(authorization, id).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    public Single<Album> getAlbum(String authorization, String id, String market) {
        return albumService.getAlbum(authorization, id, market).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    public Flowable<Album> getAlbums(String authorization, String ids) {
        return albumService.getAlbums(authorization, ids).onErrorResumeNext((Function<Throwable, Publisher<? extends Album>>) apiExceptionConverter::convertFlowable);
    }

    public Flowable<Album> getAlbums(String authorization, String ids, String market) {
        return albumService.getAlbums(authorization, ids, market).onErrorResumeNext((Function<Throwable, Publisher<? extends Album>>) apiExceptionConverter::convertFlowable);
    }

    public static class Builder implements
            SpotifySDKBuilderSteps.ClientIdStep,
            SpotifySDKBuilderSteps.ClientSecretStep,
            SpotifySDKBuilderSteps.OptionalStep {

        private Builder() {

        }

        public static SpotifySDKBuilderSteps.ClientIdStep create() {
            return new Builder();
        }

        private static final String API_BASE_URL = "https://api.spotify.com/";

        private String clientId;
        private String clientSecret;
        private boolean async;
        private Scheduler scheduler;
        private OkHttpClient okHttpClient;

        @Override
        public SpotifySDKBuilderSteps.ClientSecretStep clientId(String clientId) {
            Objects.requireNonNull(clientId);
            this.clientId = clientId;
            return this;
        }

        @Override
        public SpotifySDKBuilderSteps.OptionalStep clientSecret(String clientSecret) {
            Objects.requireNonNull(clientSecret);
            this.clientSecret = clientSecret;
            return this;
        }

        @Override
        public SpotifySDKBuilderSteps.OptionalStep async(boolean async) {
            this.async = async;
            return this;
        }


        @Override
        public SpotifySDKBuilderSteps.OptionalStep rxJavaScheduler(Scheduler scheduler) {
            Objects.requireNonNull(scheduler);
            this.scheduler = scheduler;
            return this;
        }

        @Override
        public SpotifySDKBuilderSteps.OptionalStep okHttpClient(OkHttpClient okHttpClient) {
            Objects.requireNonNull(okHttpClient);
            this.okHttpClient = okHttpClient;
            return this;
        }

        @Override
        public SpotifySDK build() {
            Retrofit retrofit = buildRetrofit();
            return new SpotifySDK(
                    clientId,
                    clientSecret,
                    retrofit.create(AlbumService.class),
                    retrofit.create(AuthorizationService.class),
                    new RxJavaExceptionConverter(new ApiErrorConverter(retrofit.responseBodyConverter(ErrorHolder.class, new Annotation[0]))),
                    new RxJavaExceptionConverter(new AuthErrorConverter(retrofit.responseBodyConverter(AuthError.class, new Annotation[0])))
            );
        }

        private Retrofit buildRetrofit() {
            return new Retrofit.Builder()
                    .addCallAdapterFactory(scheduler != null ? RxJava2CallAdapterFactory.createWithScheduler(scheduler) : async ? RxJava2CallAdapterFactory.createAsync() : RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create(new SpotifyObjectMapper()))
                    .baseUrl(API_BASE_URL)
                    .client(okHttpClient != null ? okHttpClient : new OkHttpClient())
                    .build();
        }
    }

}
