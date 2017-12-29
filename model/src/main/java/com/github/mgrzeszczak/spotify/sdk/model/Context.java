package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Context {

    private String type;

    private String href;

    private Map<String, String> externalUrls;

    private String uri;

}
