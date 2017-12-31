package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumPageContainer {

    private OffsetPage<Album> albums;

}
