package com.github.mgrzeszczak.spotify.sdk.api;

interface ThrowableHandler {

    Throwable handle(Throwable throwable) throws Exception;

}
