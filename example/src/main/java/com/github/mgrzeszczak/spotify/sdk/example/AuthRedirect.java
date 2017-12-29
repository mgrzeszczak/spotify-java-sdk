package com.github.mgrzeszczak.spotify.sdk.example;

import lombok.Data;

@Data
class AuthRedirect {

    private final String authorizationCode;
    private final String state;

    String getAuthorizationCode() {
        return authorizationCode;
    }

    String getState() {
        return state;
    }

}
