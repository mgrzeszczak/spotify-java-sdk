package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Restrictions {

    @JsonValue
    private Map<String, String> data;

}
