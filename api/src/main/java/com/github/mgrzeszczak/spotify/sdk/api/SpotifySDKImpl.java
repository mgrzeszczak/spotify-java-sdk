package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.api.service.AlbumService;
import com.github.mgrzeszczak.spotify.sdk.model.Album;

import io.reactivex.Single;

public class SpotifySDKImpl implements SpotifySDK {

    private final AlbumService albumService;
    private final ExceptionConverter exceptionConverter;

    public SpotifySDKImpl(AlbumService albumService, ExceptionConverter exceptionConverter) {
        this.albumService = albumService;
        this.exceptionConverter = exceptionConverter;
    }

    @Override
    public Single<Album> getAlbum(String token, String id) {
        return albumService.getAlbum(token, id).onErrorResumeNext(exceptionConverter::convertSingle);
    }

}
