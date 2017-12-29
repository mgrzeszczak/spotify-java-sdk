package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumContainer {

    private List<Album> albums;

}
