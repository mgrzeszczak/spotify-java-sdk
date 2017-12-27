package com.github.mgrzeszczak.spotify.sdk.api.exception;

import com.github.mgrzeszczak.spotify.sdk.model.AuthenticationError;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {

    private final AuthenticationError authenticationError;

    public AuthenticationException(Throwable cause, AuthenticationError authenticationError) {
        super(cause);
        this.authenticationError = authenticationError;
    }

    @Override
    public String getMessage() {
        return authenticationError.getError();
    }

}
