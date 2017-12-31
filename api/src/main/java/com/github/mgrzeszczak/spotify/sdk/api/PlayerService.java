package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.model.CurrentPlayback;
import com.github.mgrzeszczak.spotify.sdk.model.CurrentlyPlaying;
import com.github.mgrzeszczak.spotify.sdk.model.CursorPage;
import com.github.mgrzeszczak.spotify.sdk.model.Devices;
import com.github.mgrzeszczak.spotify.sdk.model.PlayHistory;
import com.github.mgrzeszczak.spotify.sdk.model.PlayParameters;
import com.github.mgrzeszczak.spotify.sdk.model.TransferPlaybackParameters;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

interface PlayerService {

    @GET("v1/me/player/recently-played")
    Single<CursorPage<PlayHistory>> getRecentlyPlayed(@Header("Authorization") String authorization,
                                                      @Query("limit") Integer limit,
                                                      @Query("after") Long after,
                                                      @Query("before") Long before);

    @GET("v1/me/player/devices")
    Single<Response<Devices>> getUserAvailableDevices(@Header("Authorization") String authorization);

    @GET("v1/me/player/currently-playing")
    Single<Response<CurrentlyPlaying>> getCurrentlyPlaying(@Header("Authorization") String authorization,
                                                           @Query("market") String market);


    @GET("v1/me/player")
    Single<Response<CurrentPlayback>> getCurrentPlayback(@Header("Authorization") String authorization,
                                                         @Query("market") String market);


    @PUT("v1/me/player/pause")
    Single<Response<Void>> pause(@Header("Authorization") String authorization,
                                 @Query("device_id") String deviceId);


    @PUT("v1/me/player/seek")
    Single<Response<Void>> seek(@Header("Authorization") String authorization,
                                @Query("position_ms") long positionMs,
                                @Query("device_id") String deviceId);


    @PUT("v1/me/player/setRepeat")
    Single<Response<Void>> setRepeat(@Header("Authorization") String authorization,
                                     @Query("state") String state,
                                     @Query("device_id") String deviceId);


    @PUT("v1/me/player/setVolume")
    Single<Response<Void>> setVolume(@Header("Authorization") String authorization,
                                     @Query("volume_percent") int volumePercent,
                                     @Query("device_id") String deviceId);


    @POST("v1/me/player/playNextTrack")
    Single<Response<Void>> playNextTrack(@Header("Authorization") String authorization,
                                         @Query("device_id") String deviceId);

    @POST("v1/me/player/playPreviousTrack")
    Single<Response<Void>> playPreviousTrack(@Header("Authorization") String authorization,
                                             @Query("device_id") String deviceId);


    @Headers("Content-Type: application/json")
    @PUT("v1/me/player/play")
    Single<Response<Void>> play(@Header("Authorization") String authorization,
                                @Query("device_id") String deviceId,
                                @Body PlayParameters playParameters);

    @PUT("v1/me/player/shuffle")
    Single<Response<Void>> shuffle(@Header("Authorization") String authorization,
                                   @Query("state") boolean state,
                                   @Query("device_id") String device_id);

    @Headers("Content-Type: application/json")
    @PUT("v1/me/player")
    Single<Response<Void>> transferPlayback(@Header("Authorization") String authorization,
                                            @Body TransferPlaybackParameters transferPlaybackParameters);

}
