package com.github.mgrzeszczak.spotify.sdk.api;

import java.util.Objects;

import org.reactivestreams.Publisher;

import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import lombok.Builder;

@Builder
class SpotifySDKImpl implements SpotifySDK {

    private final String clientId;
    private final String clientSecret;
    private final AlbumService albumService;
    private final AuthorizationService authorizationService;
    private final RxJavaExceptionConverter apiExceptionConverter;
    private final RxJavaExceptionConverter authExceptionConverter;

    public Single<TokenData> getToken(String authorizationCode, String redirectUri) {
        Objects.requireNonNull(authorizationCode);
        Objects.requireNonNull(redirectUri);
        return authorizationService.getToken(
                AuthorizationService.URL,
                clientId,
                clientSecret,
                AuthorizationService.CODE_GRANT_TYPE,
                authorizationCode,
                redirectUri
        ).onErrorResumeNext(authExceptionConverter::convertSingle);
    }

    public Single<TokenData> refreshToken(String refreshToken) {
        Objects.requireNonNull(refreshToken);
        return authorizationService.refreshToken(
                AuthorizationService.URL,
                clientId,
                clientSecret,
                AuthorizationService.REFRESH_TOKEN_GRANT_TYPE,
                refreshToken
        ).onErrorResumeNext(authExceptionConverter::convertSingle);
    }

    @Override
    public Single<Album> getAlbum(String authorization, String id) {
        return albumService.getAlbum(authorization, id).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Override
    public Single<Album> getAlbum(String authorization, String id, String market) {
        return albumService.getAlbum(authorization, id, market).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Override
    public Flowable<Album> getAlbums(String authorization, String ids) {
        return albumService.getAlbums(authorization, ids).onErrorResumeNext((Function<Throwable, Publisher<? extends Album>>) apiExceptionConverter::convertFlowable);
    }

    @Override
    public Flowable<Album> getAlbums(String authorization, String ids, String market) {
        return albumService.getAlbums(authorization, ids, market).onErrorResumeNext((Function<Throwable, Publisher<? extends Album>>) apiExceptionConverter::convertFlowable);
    }

}
