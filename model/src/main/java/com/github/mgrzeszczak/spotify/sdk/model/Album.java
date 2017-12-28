package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;
import java.util.Map;

import com.github.mgrzeszczak.spotify.sdk.model.enums.AlbumType;
import com.github.mgrzeszczak.spotify.sdk.model.enums.ObjectType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Album {

    private List<Copyright> copyrights;

    private Map<String, String> externalIds;

    private List<String> genres;

    private String label;

    private int popularity;

    private String releaseDate;

    private String releaseDatePrecision;

    private OffsetPage<TrackSimplified> tracks;

    private AlbumType albumType;

    private List<ArtistSimplified> artists;

    private List<String> availableMarkets;

    private Map<String, String> externalUrls;

    private String href;

    private String id;

    private List<Image> images;

    private String name;

    private ObjectType type;

    private String uri;

}
