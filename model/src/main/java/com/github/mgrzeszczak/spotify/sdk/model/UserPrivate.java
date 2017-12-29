package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;
import java.util.Map;

import com.github.mgrzeszczak.spotify.sdk.model.enums.ObjectType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPrivate {

    private String birthdate;

    private String country;

    private String email;

    private String product;

    private String displayName;

    private Map<String, String> externalUrls;

    private Followers followers;

    private String href;

    private String id;

    private List<Image> images;

    private ObjectType type;

    private String uri;

}
