package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.Map;

import com.github.mgrzeszczak.spotify.sdk.model.enums.ObjectType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtistSimplified {

    private Map<String, String> externalUrls;

    private String href;

    private String id;

    private String name;

    private ObjectType type;

    private String uri;

    private Followers followers;

}
