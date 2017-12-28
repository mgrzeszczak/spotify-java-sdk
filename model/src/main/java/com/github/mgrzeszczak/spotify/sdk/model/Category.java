package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {

    private String href;

    private List<Image> icons;

    private String id;

    private String name;

}
