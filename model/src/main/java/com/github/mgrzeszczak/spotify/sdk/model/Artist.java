package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Artist {

    private Followers followers;

    private List<String> genres;

    private List<Image> images;

    private int popularity;

    private Map<String, String> externalUrls;

    private String href;

    private String id;

    private String name;

    private String type;

    private String uri;

}
