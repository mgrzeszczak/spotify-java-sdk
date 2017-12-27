package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistTrack {

    private String addedAt;

    private UserPublic addedBy;

    private boolean isLocal;

    private Track track;

}
