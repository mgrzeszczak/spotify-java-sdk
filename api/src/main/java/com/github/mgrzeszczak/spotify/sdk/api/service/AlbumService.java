package com.github.mgrzeszczak.spotify.sdk.api.service;

import com.github.mgrzeszczak.spotify.sdk.model.Album;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AlbumService {

    @GET("v1/albums/{id}")
    Single<Album> getAlbum(@Header("Authorization") String authorization,
                                     @Path("id") String id);

    @GET("v1/albums/{id}")
    Single<Response<Album>> getAlbum(@Header("Authorization") String authorization,
                                     @Path("id") String id,
                                     @Query("market") String market);

    @GET("v1/albums")
    Observable<Album> getAlbums(@Header("Authorization") String authorization,
                                @Query("ids") String ids);

    @GET("v1/albums")
    Observable<Album> getAlbums(@Header("Authorization") String authorization,
                                @Query("ids") String ids,
                                @Query("market") String market);

}
