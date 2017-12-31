package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.model.AlbumSimplifiedPageContainer;
import com.github.mgrzeszczak.spotify.sdk.model.ArtistPageContainer;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistsPageContainer;
import com.github.mgrzeszczak.spotify.sdk.model.TracksPageContainer;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

interface SearchService {

    @GET("v1/search")
    Single<ArtistPageContainer> searchArtists(@Header("Authorization") String authorization,
                                              @Query("q") String query,
                                              @Query("type") String type,
                                              @Query("market") String market,
                                              @Query("limit") Integer limit,
                                              @Query("offset") Integer offset);

    @GET("v1/search")
    Single<AlbumSimplifiedPageContainer> searchAlbums(@Header("Authorization") String authorization,
                                                      @Query("q") String query,
                                                      @Query("type") String type,
                                                      @Query("market") String market,
                                                      @Query("limit") Integer limit,
                                                      @Query("offset") Integer offset);

    @GET("v1/search")
    Single<TracksPageContainer> searchTracks(@Header("Authorization") String authorization,
                                             @Query("q") String query,
                                             @Query("type") String type,
                                             @Query("market") String market,
                                             @Query("limit") Integer limit,
                                             @Query("offset") Integer offset);

    @GET("v1/search")
    Single<PlaylistsPageContainer> searchPlaylists(@Header("Authorization") String authorization,
                                                   @Query("q") String query,
                                                   @Query("type") String type,
                                                   @Query("market") String market,
                                                   @Query("limit") Integer limit,
                                                   @Query("offset") Integer offset);

}
