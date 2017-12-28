package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayHistory {

    private TrackSimplified track;
    private String playedAt;
    private Context context;

}
