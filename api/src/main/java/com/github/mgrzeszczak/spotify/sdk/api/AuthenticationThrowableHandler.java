package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.api.exception.AuthenticationException;
import com.github.mgrzeszczak.spotify.sdk.model.AuthenticationError;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;

class AuthenticationThrowableHandler implements ThrowableHandler {

    private final Converter<ResponseBody, AuthenticationError> converter;

    AuthenticationThrowableHandler(Converter<ResponseBody, AuthenticationError> converter) {
        this.converter = converter;
    }

    @Override
    public Throwable handle(Throwable throwable) throws Exception {
        if (throwable instanceof HttpException) {
            AuthenticationError error = converter.convert(((HttpException) throwable).response().errorBody());
            return new AuthenticationException(throwable, error);
        } else {
            return throwable;
        }
    }
}
