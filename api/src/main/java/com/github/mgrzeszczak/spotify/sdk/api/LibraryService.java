package com.github.mgrzeszczak.spotify.sdk.api;

import java.util.List;

import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;
import com.github.mgrzeszczak.spotify.sdk.model.SavedAlbum;
import com.github.mgrzeszczak.spotify.sdk.model.SavedTrack;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

interface LibraryService {

    @GET("v1/me/albums/contains")
    Single<List<Boolean>> checkCurrentUserSavedAlbums(@Header("Authorization") String authorization,
                                                      @Query("ids") String ids);


    @GET("v1/me/tracks/contains")
    Single<List<Boolean>> checkCurrentUserSavedTracks(@Header("Authorization") String authorization,
                                                      @Query("ids") String ids);

    @GET("v1/me/albums")
    Single<OffsetPage<SavedAlbum>> getSavedAlbums(@Header("Authorization") String authorization,
                                                  @Query("limit") Integer limit,
                                                  @Query("offset") Integer offset,
                                                  @Query("market") String market);

    @GET("v1/me/tracks")
    Single<OffsetPage<SavedTrack>> getSavedTracks(@Header("Authorization") String authorization,
                                                  @Query("limit") Integer limit,
                                                  @Query("offset") Integer offset,
                                                  @Query("market") String market);

    @DELETE("v1/me/albums")
    Completable removeAlbums(@Header("Authorization") String authorization,
                             @Query("ids") String ids);

    @DELETE("v1/me/tracks")
    Completable removeTracks(@Header("Authorization") String authorization,
                             @Query("ids") String ids);

    @PUT("v1/me/albums")
    Completable saveAlbums(@Header("Authorization") String authorization,
                           @Query("ids") String ids);

    @PUT("v1/me/tracks")
    Completable saveTracks(@Header("Authorization") String authorization,
                           @Query("ids") String ids);

}


