package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.model.Artist;
import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;
import com.github.mgrzeszczak.spotify.sdk.model.Track;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface PersonalizationService {

    @GET("v1/me/top/{type}")
    Single<OffsetPage<Artist>> getCurrentUserTopArtists(@Header("Authorization") String authorization,
                                                        @Path("type") String type,
                                                        @Query("limit") Integer limit,
                                                        @Query("offset") Integer offset,
                                                        @Query("time_range") String timeRange);

    @GET("v1/me/top/{type}")
    Single<OffsetPage<Track>> getCurrentUserTopTracks(@Header("Authorization") String authorization,
                                                      @Path("type") String type,
                                                      @Query("limit") Integer limit,
                                                      @Query("offset") Integer offset,
                                                      @Query("time_range") String timeRange);

}
