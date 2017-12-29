package com.github.mgrzeszczak.spotify.sdk.api;

import java.util.Map;

import com.github.mgrzeszczak.spotify.sdk.model.AlbumSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.Category;
import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.Recommendations;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

interface BrowseService {

    @GET("v1/browse/categories/{category_id}")
    Single<Category> getCategory(@Header("Authorization") String authorization,
                                 @Path("category_id") String categoryId,
                                 @Query("country") String country,
                                 @Query("locale") String locale);

    @GET("v1/browse/categories/{category_id}/playlists")
    Single<OffsetPage<PlaylistSimplified>> getCategoryPlaylists(@Header("Authorization") String authorization,
                                                                @Path("category_id") String categoryId,
                                                                @Query("country") String country,
                                                                @Query("limit") Integer limit,
                                                                @Query("offset") Integer offset);


    @GET("v1/browse/categories")
    Single<OffsetPage<Category>> getCategories(@Header("Authorization") String authorization,
                                               @Query("locale") String locale,
                                               @Query("country") String country,
                                               @Query("limit") Integer limit,
                                               @Query("offset") Integer offset);


    @GET("v1/browse/featured-playlists")
    Single<OffsetPage<PlaylistSimplified>> getFeaturedPlaylists(@Header("Authorization") String authorization,
                                                                @Query("locale") String locale,
                                                                @Query("country") String country,
                                                                @Query("timestamp") String timestamp,
                                                                @Query("limit") Integer limit,
                                                                @Query("offset") Integer offset);

    @GET("v1/browse/new-releases")
    Single<OffsetPage<AlbumSimplified>> getNewReleases(@Header("Authorization") String authorization,
                                                       @Query("country") String country,
                                                       @Query("limit") Integer limit,
                                                       @Query("offset") Integer offset);

    @GET("v1/recommendations")
    Single<Recommendations> getRecommendations(@Header("Authorization") String authorization,
                                               @Query("seed_artists") String seedArtists,
                                               @Query("seed_genres") String seedGenres,
                                               @Query("seed_tracks") String seedTracks,
                                               @Query("limit") String limit,
                                               @Query("market") String market,
                                               @QueryMap Map<String, Object> minTrackAttributes,
                                               @QueryMap Map<String, Object> maxTrackAttributes,
                                               @QueryMap Map<String, Object> targetTrackAttributes);

}
