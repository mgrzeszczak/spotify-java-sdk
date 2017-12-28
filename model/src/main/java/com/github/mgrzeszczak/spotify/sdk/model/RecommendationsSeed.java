package com.github.mgrzeszczak.spotify.sdk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommendationsSeed {

    @JsonProperty("afterFilteringSize")
    private int afterFilteringSize;

    @JsonProperty("afterRelinkingSize")
    private int afterRelinkingSize;

    private String href;

    private String id;

    @JsonProperty("initialPoolSize")
    private int initialPoolSize;

    private String type;

}
