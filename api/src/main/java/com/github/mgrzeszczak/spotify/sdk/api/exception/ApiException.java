package com.github.mgrzeszczak.spotify.sdk.api.exception;

import com.github.mgrzeszczak.spotify.sdk.model.Error;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final Error error;

    public ApiException(Throwable cause, Error error) {
        super(cause);
        this.error = error;
    }

    @Override
    public String getMessage() {
        return error.getMessage();
    }

}
