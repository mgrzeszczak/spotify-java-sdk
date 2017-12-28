package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.api.exception.ApiException;
import com.github.mgrzeszczak.spotify.sdk.model.ErrorHolder;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class ApiErrorConverter implements ErrorConverter {

    private final Converter<ResponseBody, ErrorHolder> converter;

    ApiErrorConverter(Converter<ResponseBody, ErrorHolder> converter) {
        this.converter = converter;
    }

    @Override
    public Throwable convertResponseBody(Throwable throwable, ResponseBody body) throws Exception {
        ErrorHolder errorHolder = converter.convert(body);
        return new ApiException(throwable, errorHolder.getError());
    }
}
