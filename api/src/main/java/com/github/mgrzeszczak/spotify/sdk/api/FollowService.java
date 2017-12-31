package com.github.mgrzeszczak.spotify.sdk.api;

import static com.github.mgrzeszczak.spotify.sdk.api.Utils.CONTENT_TYPE_APPLICATION_JSON;

import java.util.List;

import com.github.mgrzeszczak.spotify.sdk.model.Artist;
import com.github.mgrzeszczak.spotify.sdk.model.CursorPage;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface FollowService {

    @GET("v1/me/following/contains")
    Single<List<Boolean>> checkIfCurrentUserFollows(@Header("Authorization") String authorization,
                                                    @Query("type") String type,
                                                    @Query("ids") String ids);

    @GET("v1/users/{owner_id}/playlists/{playlist_id}/followers/contains")
    Single<List<Boolean>> checkIfUsersFollowPlaylist(@Header("Authorization") String authorization,
                                                     @Path("owner_id") String ownerId,
                                                     @Path("playlist_id") String playlistId,
                                                     @Query("ids") String userIds);

    @PUT("v1/me/following")
    Completable followArtistOrUser(@Header("Authorization") String authorization,
                                   @Query("type") String type,
                                   @Query("ids") String ids);

    @DELETE("v1/me/following")
    Completable unfollowArtistOrUser(@Header("Authorization") String authorization,
                                     @Query("type") String type,
                                     @Query("ids") String ids);

    @Headers(CONTENT_TYPE_APPLICATION_JSON)
    @PUT("v1/users/{owner_id}/playlists/{playlist_id}/followers")
    Completable followPlaylist(@Header("Authorization") String authorization,
                               @Path("owner_id") String ownerId,
                               @Path("playlist_id") String playlistId,
                               @Body boolean includeInPublicPlaylists);

    @DELETE("v1/users/{owner_id}/playlists/{playlist_id}/followers")
    Completable unfollowPlaylist(@Header("Authorization") String authorization,
                                 @Path("owner_id") String ownerId,
                                 @Path("playlist_id") String playlistId);


    @GET("v1/me/following")
    Single<Wrapper<CursorPage<Artist>>> getCurrentUserFollowedArtists(@Header("Authorization") String authorization,
                                                                      @Query("type") String type,
                                                                      @Query("limit") String limit,
                                                                      @Query("after") String after);


}
