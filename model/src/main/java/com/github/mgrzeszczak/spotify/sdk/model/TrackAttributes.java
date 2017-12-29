package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackAttributes {

    private Float acousticness;
    private Float danceability;
    private Integer durationMs;
    private Float energy;
    private Float instrumentalness;
    private Integer key;
    private Float liveness;
    private Float loudness;
    private Integer mode;
    private Integer popularity;
    private Float speechiness;
    private Float tempo;
    private Integer timeSignature;
    private Float valence;

}
