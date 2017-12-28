package com.github.mgrzeszczak.spotify.sdk.api;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.mgrzeszczak.spotify.sdk.model.authorization.Scope;

class AuthorizationCodeRequestURLBuilderImpl implements AuthorizationCodeRequestURLBuilder {

    private static final String BASE_URL = "https://accounts.spotify.com/authorize";
    private String clientId;
    private String responseType;
    private String redirectUri;
    private Collection<Scope> scopes;
    private String state;
    private boolean showDialog;

    AuthorizationCodeRequestURLBuilderImpl() {
    }

    @Override
    public AuthorizationCodeRequestURLBuilderSteps.ResponseTypeStep clientId(String clientId) {
        Objects.requireNonNull(clientId);
        this.clientId = clientId;
        return this;
    }

    @Override
    public AuthorizationCodeRequestURLBuilderSteps.RedirectUriStep responseType(String responseType) {
        Objects.requireNonNull(responseType);
        this.responseType = responseType;
        return this;
    }

    @Override
    public AuthorizationCodeRequestURLBuilderSteps.OptionalDataStep redirectUri(String redirectUri) {
        Objects.requireNonNull(redirectUri);
        this.redirectUri = redirectUri;
        return this;
    }

    @Override
    public String build() {
        String url = BASE_URL
                + "?client_id=" + clientId
                + "&response_type=" + responseType
                + "&redirect_uri=" + redirectUri
                + "&show_dialog=" + showDialog;
        if (scopes != null) {
            url = url + "&scope=" + scopes.stream().map(Scope::getValue).collect(Collectors.joining("%20"));
        }
        if (state != null) {
            url = url + "&state=" + state;
        }
        return url;
    }

    @Override
    public AuthorizationCodeRequestURLBuilderSteps.OptionalDataStep showDialog(boolean showDialog) {
        this.showDialog = showDialog;
        return this;
    }

    @Override
    public AuthorizationCodeRequestURLBuilderSteps.OptionalDataStep requestedScopes(Collection<Scope> scopes) {
        Objects.requireNonNull(scopes);
        if (scopes.isEmpty()) {
            throw new IllegalArgumentException("scopes must not be empty");
        }
        this.scopes = scopes;
        return this;
    }

    @Override
    public AuthorizationCodeRequestURLBuilderSteps.OptionalDataStep state(String state) {
        Objects.requireNonNull(state);
        this.state = state;
        return this;
    }
}
