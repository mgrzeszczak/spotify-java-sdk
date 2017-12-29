package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class CursorPage<T> extends Page<T> {

    private Cursor cursors;

}
