package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.Map;

import com.github.mgrzeszczak.spotify.sdk.model.enums.ObjectType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Context {

    private ObjectType type;

    private String href;

    private Map<String, String> externalUrls;

    private String uri;

}
