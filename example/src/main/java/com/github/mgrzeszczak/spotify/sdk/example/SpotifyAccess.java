package com.github.mgrzeszczak.spotify.sdk.example;

import com.github.mgrzeszczak.spotify.sdk.api.SpotifySDK;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class SpotifyAccess {

    private final SpotifySDK spotify;
    private final TokenData tokenData;

    SpotifyAccess(SpotifySDK spotify, TokenData tokenData) {
        this.spotify = spotify;
        this.tokenData = tokenData;
    }
}
