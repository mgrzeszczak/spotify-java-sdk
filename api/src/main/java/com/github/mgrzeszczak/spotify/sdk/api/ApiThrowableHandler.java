package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.api.exception.ApiException;
import com.github.mgrzeszczak.spotify.sdk.model.ErrorHolder;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;

class ApiThrowableHandler implements ThrowableHandler {

    private final Converter<ResponseBody, ErrorHolder> converter;

    ApiThrowableHandler(Converter<ResponseBody, ErrorHolder> converter) {
        this.converter = converter;
    }

    @Override
    public Throwable handle(Throwable throwable) throws Exception {
        if (throwable instanceof HttpException) {
            ErrorHolder errorHolder = converter.convert(((HttpException) throwable).response().errorBody());
            return new ApiException(throwable, errorHolder.getError());
        } else {
            return throwable;
        }
    }

}
