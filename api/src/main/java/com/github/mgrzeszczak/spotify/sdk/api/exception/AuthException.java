package com.github.mgrzeszczak.spotify.sdk.api.exception;

import com.github.mgrzeszczak.spotify.sdk.model.authorization.AuthError;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final AuthError authError;

    public AuthException(Throwable cause, AuthError authError) {
        super(cause);
        this.authError = authError;
    }

    @Override
    public String getMessage() {
        return authError.getError();
    }

}
