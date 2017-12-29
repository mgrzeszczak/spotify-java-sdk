package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.Artist;
import com.github.mgrzeszczak.spotify.sdk.model.Artists;
import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;
import com.github.mgrzeszczak.spotify.sdk.model.Tracks;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ArtistService {

    @GET("v1/artists/{id}")
    Single<Artist> getArtist(@Header("Authorization") String authorization,
                             @Path("id") String id);

    @GET("v1/artists")
    Single<Artists> getArtists(@Header("Authorization") String authorization,
                               @Query("ids") String ids);


    @GET("v1/artists/{id}/albums")
    Single<OffsetPage<Album>> getArtistAlbums(@Header("Authorization") String authorization,
                                              @Path("id") String id,
                                              @Query("album_type") String albumType,
                                              @Query("market") String market,
                                              @Query("limit") Integer limit,
                                              @Query("offset") Integer offset);


    @GET("v1/artists/{id}/top-tracks")
    Single<Tracks> getArtistTopTracks(@Header("Authorization") String authorization,
                                      @Path("id") String id,
                                      @Query("country") String country);


    @GET("v1/artists/{id}/related-artists")
    Single<Artists> getRelatedArtists(@Header("Authorization") String authorization,
                                      @Path("id") String id);


}
