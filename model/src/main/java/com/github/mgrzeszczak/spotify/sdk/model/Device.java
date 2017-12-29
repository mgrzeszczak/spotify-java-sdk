package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Device {

    private String id;

    private boolean isActive;

    private boolean isRestricted;

    private String name;

    private String type;

    private Integer volumePercent;

}
