package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayParameters {

    private String contextUri;

    private List<String> uris;

    private Offset offset;

    @Data
    @Builder
    public static class Offset {

        private Integer position;

        private String uri;

    }

}
