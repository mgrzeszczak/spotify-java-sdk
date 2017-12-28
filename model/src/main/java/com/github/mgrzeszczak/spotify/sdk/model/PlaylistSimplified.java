package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mgrzeszczak.spotify.sdk.model.enums.ObjectType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistSimplified {

    private boolean collaborative;

    private Map<String, String> externalUrls;

    private String href;

    private String id;

    private List<Image> images;

    private String name;

    private UserPublic owner;

    @JsonProperty("public")
    private Boolean publicStatus;

    private String snapshotId;

    private List<Track> tracks;

    private ObjectType type;

    private String uri;

}
