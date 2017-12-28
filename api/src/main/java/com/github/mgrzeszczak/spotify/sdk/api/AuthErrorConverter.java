package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.api.exception.AuthException;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.AuthError;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class AuthErrorConverter implements ErrorConverter {

    private final Converter<ResponseBody, AuthError> converter;

    AuthErrorConverter(Converter<ResponseBody, AuthError> converter) {
        this.converter = converter;
    }

    @Override
    public Throwable convertResponseBody(Throwable throwable, ResponseBody body) throws Exception {
        AuthError error = converter.convert(body);
        return new AuthException(throwable, error);
    }
}
