package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPublic {

    private String displayName;

    private Map<String, String> externalUrls;

    private Followers followers;

    private String href;

    private String id;

    private List<Image> images;

    private String type;

    private String uri;

}
