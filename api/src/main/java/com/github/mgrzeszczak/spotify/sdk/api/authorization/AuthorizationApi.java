package com.github.mgrzeszczak.spotify.sdk.api.authorization;

import java.lang.annotation.Annotation;
import java.util.Objects;

import com.github.mgrzeszczak.spotify.sdk.api.SpotifyObjectMapper;
import com.github.mgrzeszczak.spotify.sdk.api.exception.AuthenticationException;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.AuthenticationError;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AuthorizationApi {

    private final String clientId;
    private final String clientSecret;
    private final Retrofit retrofit;
    private final AuthorizationService authorizationService;
    private final Converter<ResponseBody, AuthenticationError> converter;

    private AuthorizationApi(String clientId, String clientSecret) {
        Objects.requireNonNull(clientId);
        Objects.requireNonNull(clientSecret);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(new SpotifyObjectMapper()))
                .baseUrl(AuthorizationService.BASE_PATH)
                .client(new OkHttpClient())
                .build();
        this.authorizationService = retrofit.create(AuthorizationService.class);
        this.converter = retrofit.responseBodyConverter(AuthenticationError.class, new Annotation[0]);
    }

    public static AuthorizationApi create(String clientId, String clientSecret) {
        return new AuthorizationApi(clientId, clientSecret);
    }

    public Single<TokenData> getToken(String authorizationCode, String redirectUri) {
        return authorizationService.getToken(clientId, clientSecret, AuthorizationService.CODE_GRANT_TYPE, authorizationCode, redirectUri)
                .onErrorResumeNext(this::onError);
    }

    public Single<TokenData> refreshToken(String refreshToken) {
        return authorizationService.refreshToken(clientId, clientSecret, AuthorizationService.REFRESH_TOKEN_GRANT_TYPE, refreshToken)
                .onErrorResumeNext(this::onError);
    }

    private SingleSource<TokenData> onError(Throwable throwable) throws Exception {
        if (throwable instanceof HttpException) {
            ResponseBody body = ((HttpException) throwable).response().errorBody();
            if (body != null) {
                AuthenticationError error = converter.convert(body);
                return Single.error(new AuthenticationException(throwable, error));
            }
        }
        return Single.error(throwable);
    }

}
