package com.github.mgrzeszczak.spotify.sdk.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AlbumType {

    ALBUM,
    SINGLE,
    COMPILATION;

    @JsonValue
    private String getValue() {
        return name().toLowerCase();
    }
}
