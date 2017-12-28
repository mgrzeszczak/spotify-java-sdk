package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Image {

    private Integer height;

    private String url;

    private Integer width;

}
