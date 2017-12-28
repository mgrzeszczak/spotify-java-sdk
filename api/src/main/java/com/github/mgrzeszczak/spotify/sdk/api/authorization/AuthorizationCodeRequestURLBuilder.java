package com.github.mgrzeszczak.spotify.sdk.api.authorization;


public interface AuthorizationCodeRequestURLBuilder extends
        AuthorizationCodeRequestURLBuilderSteps.ClientIdStep,
        AuthorizationCodeRequestURLBuilderSteps.ResponseTypeStep,
        AuthorizationCodeRequestURLBuilderSteps.RedirectUriStep,
        AuthorizationCodeRequestURLBuilderSteps.OptionalDataStep {

    String CODE_RESPONSE_TYPE = "code";

    static AuthorizationCodeRequestURLBuilderSteps.ClientIdStep create() {
        return new AuthorizationCodeRequestURLBuilderImpl();
    }

}
