package com.github.mgrzeszczak.spotify.sdk.api;

import java.lang.annotation.Annotation;

import org.reactivestreams.Publisher;

import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.AuthenticationError;
import com.github.mgrzeszczak.spotify.sdk.model.ErrorHolder;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

class SpotifySDKImpl implements SpotifySDK {

    private final AlbumService albumService;
    private final ExceptionConverter apiExceptionConverter;
    private final ExceptionConverter authExceptionConverter;
    private final Retrofit retrofit;

    SpotifySDKImpl(Retrofit retrofit) {
        this.retrofit = retrofit;
        this.albumService = retrofit.create(AlbumService.class);
        this.apiExceptionConverter = new ExceptionConverter(new ApiThrowableHandler(retrofit.responseBodyConverter(ErrorHolder.class, new Annotation[0])));
        this.authExceptionConverter = new ExceptionConverter(new AuthenticationThrowableHandler(retrofit.responseBodyConverter(AuthenticationError.class, new Annotation[0])));
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
