package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackLink {

    private Map<String, String> externalUrls;

    private String href;

    private String id;

    private String type;

    private String uri;

}
