package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.AlbumContainer;
import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;
import com.github.mgrzeszczak.spotify.sdk.model.Track;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface AlbumService {

    @GET("v1/albums/{id}")
    Single<Album> getAlbum(@Header("Authorization") String authorization,
                           @Path("id") String id,
                           @Query("market") String market);

    @GET("v1/albums")
    Single<AlbumContainer> getAlbums(@Header("Authorization") String authorization,
                                     @Query("ids") String ids,
                                     @Query("market") String market);

    @GET("albums/{id}/tracks")
    Single<OffsetPage<Track>> getAlbumTracks(@Header("Authorization") String authorization,
                                             @Query("id") String ids,
                                             @Query("limit") Integer limit,
                                             @Query("offset") Integer offset,
                                             @Query("market") String market);

}
