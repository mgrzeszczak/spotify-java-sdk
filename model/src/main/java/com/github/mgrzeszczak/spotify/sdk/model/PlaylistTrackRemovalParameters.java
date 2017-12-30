package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistTrackRemovalParameters {

    private List<RemoveTrack> tracks;

    private String snapshotId;

    @Data
    @Builder
    public static class RemoveTrack {

        private String uri;

        private List<Integer> positions;

    }

}
