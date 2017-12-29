package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtistSimplified {

    private Map<String, String> externalUrls;

    private String href;

    private String id;

    private String name;

    private String type;

    private String uri;

    private Followers followers;

}
