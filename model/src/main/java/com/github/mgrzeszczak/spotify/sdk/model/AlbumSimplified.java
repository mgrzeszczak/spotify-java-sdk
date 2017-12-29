package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumSimplified {

    private String albumType;

    private List<ArtistSimplified> artists;

    private List<String> availableMarkets;

    private Map<String, String> externalUrls;

    private String href;

    private String id;

    private List<Image> images;

    private String name;

    private String type;

    private String uri;

}
