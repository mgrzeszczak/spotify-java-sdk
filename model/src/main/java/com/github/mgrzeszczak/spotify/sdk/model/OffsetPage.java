package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class OffsetPage<T> extends Page<T> {

    private int offset;

    private String previous;

}
