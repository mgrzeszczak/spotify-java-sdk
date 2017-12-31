package com.github.mgrzeszczak.spotify.sdk.api;

import java.util.Map;

import com.github.mgrzeszczak.spotify.sdk.model.AudioFeatures;
import com.github.mgrzeszczak.spotify.sdk.model.AudioFeaturesContainer;
import com.github.mgrzeszczak.spotify.sdk.model.Track;
import com.github.mgrzeszczak.spotify.sdk.model.TrackContainer;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface TrackService {

    @GET("v1/tracks/{id}")
    Single<Track> getTrack(@Header("Authorization") String authorization,
                           @Path("id") String trackId,
                           @Query("market") String market);

    @GET("v1/tracks")
    Single<TrackContainer> getTracks(@Header("Authorization") String authorization,
                                     @Query("ids") String trackIds,
                                     @Query("market") String market);


    @GET("v1/audio-features/{id}")
    Single<AudioFeatures> getTrackAudioFeatures(@Header("Authorization") String authorization,
                                                @Path("id") String trackId);

    @GET("v1/audio-features")
    Single<AudioFeaturesContainer> getTracksAudioFeatures(@Header("Authorization") String authorization,
                                                          @Query("ids") String trackIds);

    @GET("v1/audio-analysis/{id}")
    Single<Map<String, Object>> getTrackAudioAnalysis(@Header("Authorization") String authorization,
                                                      @Path("id") String trackId);


}
