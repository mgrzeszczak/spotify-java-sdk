package com.github.mgrzeszczak.spotify.sdk.model;

import org.jetbrains.annotations.Nullable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistTrackReorderParameters {

    private int rangeStart;

    @Nullable
    private Integer rangeLength;

    private int insertBefore;

    @Nullable
    private String snapshotId;

}
