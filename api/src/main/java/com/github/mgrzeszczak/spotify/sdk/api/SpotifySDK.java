package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.model.Album;

import io.reactivex.Single;

public interface SpotifySDK {

    Single<Album> getAlbum(String token, String id);

}
