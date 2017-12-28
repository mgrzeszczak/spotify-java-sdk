package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;
import java.util.Map;

import com.github.mgrzeszczak.spotify.sdk.model.enums.ObjectType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackSimplified {


    private List<ArtistSimplified> artists;

    private List<String> availableMarkets;

    private int discNumber;

    private int durationMs;

    private boolean explicit;

    private Map<String, String> externalUrls;

    private String href;

    private String id;

    private boolean isPlayable;

    private TrackLink linkedFrom;

    private String name;

    private String previewUrl;

    private int trackNumber;

    private ObjectType type;

    private String uri;

}
