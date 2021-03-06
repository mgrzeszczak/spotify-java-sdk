package com.github.mgrzeszczak.spotify.sdk.api;

import java.util.Collection;

import com.github.mgrzeszczak.spotify.sdk.model.authorization.Scope;

public interface SpotifyAuthCodeURLRequestBuilderSteps {

    interface ClientIdStep {

        ResponseTypeStep clientId(String clientId);

    }

    interface ResponseTypeStep {

        RedirectUriStep responseType(String responseType);

    }

    interface RedirectUriStep {

        OptionalDataStep redirectUri(String redirectUri);
    }

    interface OptionalDataStep {
        String build();

        OptionalDataStep showDialog(boolean showDialog);

        OptionalDataStep requestedScopes(Collection<Scope> scopes);

        OptionalDataStep state(String state);
    }

}
