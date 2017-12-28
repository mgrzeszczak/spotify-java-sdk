package com.github.mgrzeszczak.spotify.sdk.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CopyrightType {

    C,
    P;

    @JsonValue
    public String getValue() {
        return name();
    }

}
