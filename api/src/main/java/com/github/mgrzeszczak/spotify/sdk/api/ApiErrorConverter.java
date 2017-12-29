package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.api.exception.ApiException;
import com.github.mgrzeszczak.spotify.sdk.model.ErrorHolder;

import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import retrofit2.Converter;

@Log4j2
class ApiErrorConverter implements ErrorConverter {

    private final Converter<ResponseBody, ErrorHolder> converter;

    ApiErrorConverter(Converter<ResponseBody, ErrorHolder> converter) {
        this.converter = converter;
    }

    @Override
    public Throwable convertResponseBody(Throwable throwable, ResponseBody body) throws Exception {
        ErrorHolder errorHolder = converter.convert(body);
        log.error("API error: {} - {}", errorHolder.getError().getMessage(), errorHolder.getError().getStatus());
        return new ApiException(throwable, errorHolder.getError());
    }
}
