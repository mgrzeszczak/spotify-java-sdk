package com.github.mgrzeszczak.spotify.sdk.api;


public interface SpotifyAuthCodeURLRequestBuilder extends
        SpotifyAuthCodeURLRequestBuilderSteps.ClientIdStep,
        SpotifyAuthCodeURLRequestBuilderSteps.ResponseTypeStep,
        SpotifyAuthCodeURLRequestBuilderSteps.RedirectUriStep,
        SpotifyAuthCodeURLRequestBuilderSteps.OptionalDataStep {

    String CODE_RESPONSE_TYPE = "code";

    static SpotifyAuthCodeURLRequestBuilderSteps.ClientIdStep create() {
        return new SpotifyAuthCodeURLRequestBuilderImpl();
    }

}
