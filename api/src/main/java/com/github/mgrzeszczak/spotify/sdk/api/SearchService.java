package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.model.AlbumSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.Artist;
import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.Track;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

interface SearchService {

    @GET("v1/search")
    Single<OffsetPage<Artist>> searchArtists(@Header("Authorization") String authorization,
                                             @Query("q") String query,
                                             @Query("type") String type,
                                             @Query("market") String market,
                                             @Query("limit") Integer limit,
                                             @Query("offset") Integer offset);

    @GET("v1/search")
    Single<OffsetPage<AlbumSimplified>> searchAlbums(@Header("Authorization") String authorization,
                                                     @Query("q") String query,
                                                     @Query("type") String type,
                                                     @Query("market") String market,
                                                     @Query("limit") Integer limit,
                                                     @Query("offset") Integer offset);

    @GET("v1/search")
    Single<OffsetPage<Track>> searchTracks(@Header("Authorization") String authorization,
                                           @Query("q") String query,
                                           @Query("type") String type,
                                           @Query("market") String market,
                                           @Query("limit") Integer limit,
                                           @Query("offset") Integer offset);

    @GET("v1/search")
    Single<OffsetPage<PlaylistSimplified>> searchPlaylists(@Header("Authorization") String authorization,
                                                           @Query("q") String query,
                                                           @Query("type") String type,
                                                           @Query("market") String market,
                                                           @Query("limit") Integer limit,
                                                           @Query("offset") Integer offset);

}
