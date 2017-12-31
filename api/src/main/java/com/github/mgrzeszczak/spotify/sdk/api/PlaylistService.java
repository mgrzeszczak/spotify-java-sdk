package com.github.mgrzeszczak.spotify.sdk.api;

import static com.github.mgrzeszczak.spotify.sdk.api.Utils.CONTENT_TYPE_APPLICATION_JSON;

import java.util.List;

import com.github.mgrzeszczak.spotify.sdk.model.Image;
import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;
import com.github.mgrzeszczak.spotify.sdk.model.Playlist;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistParameters;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistTrack;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistTrackRemovalParameters;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistTrackReorderParameters;
import com.github.mgrzeszczak.spotify.sdk.model.SnapshotId;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface PlaylistService {

    @POST("v1/users/{user_id}/playlists/{playlist_id}/tracks")
    Single<SnapshotId> addTracksToPlaylist(@Header("Authorization") String authorization,
                                           @Path("user_id") String userId,
                                           @Path("playlist_id") String playlistId,
                                           @Query("uris") String uris,
                                           @Query("position") Integer position);

    @Headers(CONTENT_TYPE_APPLICATION_JSON)
    @PUT("v1/users/{user_id}/playlists/{playlist_id}")
    Completable changePlaylistDetails(@Header("Authorization") String authorization,
                                      @Path("user_id") String userId,
                                      @Path("playlist_id") String playlistId,
                                      @Body PlaylistParameters playlistParameters);

    @Headers(CONTENT_TYPE_APPLICATION_JSON)
    @POST("v1/users/{user_id}/playlists")
    Single<Playlist> createPlaylist(@Header("Authorization") String authorization,
                                    @Path("user_id") String userId,
                                    @Body PlaylistParameters playlistParameters);

    @GET("v1/me/playlists")
    Single<OffsetPage<PlaylistSimplified>> getCurrentUserPlaylists(@Header("Authorization") String authorization,
                                                                   @Query("limit") Integer limit,
                                                                   @Query("offset") Integer offset);

    @GET("v1/users/{user_id}/playlists")
    Single<OffsetPage<PlaylistSimplified>> getUserPlaylists(@Header("Authorization") String authorization,
                                                            @Path("user_id") String userId,
                                                            @Query("limit") Integer limit,
                                                            @Query("offset") Integer offset);


    @GET("v1/users/{user_id}/playlists/{playlist_id}/images")
    Single<List<Image>> getPlaylistCoverImages(@Header("Authorization") String authorization,
                                               @Path("user_id") String userId,
                                               @Path("playlist_id") String playlistId);


    @GET("v1/users/{user_id}/playlists/{playlist_id}")
    Single<Playlist> getPlaylist(@Header("Authorization") String authorization,
                                 @Path("user_id") String userId,
                                 @Path("playlist_id") String playlistId,
                                 @Query("fields") String fields,
                                 @Query("market") String market);

    @GET("v1/users/{user_id}/playlists/{playlist_id}/tracks")
    Single<OffsetPage<PlaylistTrack>> getPlaylistTracks(@Header("Authorization") String authorization,
                                                        @Path("user_id") String userId,
                                                        @Path("playlist_id") String playlistId,
                                                        @Query("fields") String fields,
                                                        @Query("limit") Integer limit,
                                                        @Query("offset") Integer offset,
                                                        @Query("market") String market);

    @Headers(CONTENT_TYPE_APPLICATION_JSON)
    @DELETE("v1/users/{user_id}/playlists/{playlist_id}/tracks")
    Single<SnapshotId> removeTracksFromPlaylist(@Header("Authorization") String authorization,
                                                @Path("user_id") String userId,
                                                @Path("playlist_id") String playlistId,
                                                @Body PlaylistTrackRemovalParameters parameters);


    @PUT("v1/users/{user_id}/playlists/{playlist_id}/tracks")
    Single<SnapshotId> reorderPlaylistTracks(@Header("Authorization") String authorization,
                                             @Path("user_id") String userId,
                                             @Path("playlist_id") String playlistId,
                                             @Body PlaylistTrackReorderParameters parameters);

    @PUT("v1/users/{user_id}/playlists/{playlist_id}/tracks")
    Completable replacePlaylistTracks(@Header("Authorization") String authorization,
                                      @Path("user_id") String userId,
                                      @Path("playlist_id") String playlistId,
                                      @Query("uris") String uris);

    @Headers("Content-Type: image/jpeg")
    @PUT("v1/users/{user_id}/playlists/{playlist_id}/images")
    Completable updatePlaylistCover(@Header("Authorization") String authorization,
                                    @Path("user_id") String userId,
                                    @Path("playlist_id") String playlistId,
                                    @Body String base64Image);

}
