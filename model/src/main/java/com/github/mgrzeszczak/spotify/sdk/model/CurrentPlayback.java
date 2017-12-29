package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentPlayback {

    private Device device;

    private Context context;

    private String repeatState;

    private boolean shuffleState;

    private long timestamp;

    private Integer progressMs;

    private boolean isPlaying;

    private Track item;

}
