package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeaturedPlaylists {

    private String message;
    private OffsetPage<PlaylistSimplified> playlists;

}
