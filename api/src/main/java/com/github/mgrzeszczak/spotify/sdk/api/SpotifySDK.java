package com.github.mgrzeszczak.spotify.sdk.api;

import java.lang.annotation.Annotation;
import java.util.Objects;

import com.github.mgrzeszczak.spotify.sdk.model.ErrorHolder;

import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public interface SpotifySDK extends AlbumService {

    class Builder implements
            SpotifySDKBuilderSteps.ClientIdStep,
            SpotifySDKBuilderSteps.ClientSecretStep,
            SpotifySDKBuilderSteps.OptionalStep {

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
            return SpotifySDKImpl.builder()
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .apiExceptionConverter(new RxJavaExceptionConverter(new ApiErrorConverter(retrofit.responseBodyConverter(ErrorHolder.class, new Annotation[0]))))
                    .authExceptionConverter(new RxJavaExceptionConverter(new AuthErrorConverter(retrofit.responseBodyConverter(ErrorHolder.class, new Annotation[0]))))
                    .albumService(retrofit.create(AlbumService.class))
                    .authorizationService(retrofit.create(AuthorizationService.class))
                    .build();
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
