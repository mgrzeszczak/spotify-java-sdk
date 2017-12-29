package com.github.mgrzeszczak.spotify.sdk.api.exception;

public class SpotifySDKException extends RuntimeException {

    public SpotifySDKException(String message) {
        super(message);
    }

    public SpotifySDKException(String message, Throwable cause) {
        super(message, cause);
    }
}
