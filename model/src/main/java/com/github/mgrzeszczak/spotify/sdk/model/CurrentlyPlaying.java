package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentlyPlaying {

    private Context context;

    private long timestamp;

    private Integer progressMs;

    private boolean isPlaying;

    private Track item;

}
