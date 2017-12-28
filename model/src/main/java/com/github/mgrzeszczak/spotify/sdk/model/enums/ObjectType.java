package com.github.mgrzeszczak.spotify.sdk.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ObjectType {

    ALBUM,
    ARTIST,
    PLAYLIST,
    TRACK,
    USER;

    @JsonValue
    private String getValue() {
        return name().toLowerCase();
    }

}
