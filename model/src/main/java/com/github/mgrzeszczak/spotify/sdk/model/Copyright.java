package com.github.mgrzeszczak.spotify.sdk.model;

import com.github.mgrzeszczak.spotify.sdk.model.enums.CopyrightType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Copyright {

    private String text;

    private CopyrightType type;

}
