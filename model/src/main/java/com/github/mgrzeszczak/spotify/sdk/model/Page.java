package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;

import lombok.Data;

@Data
public abstract class Page<T> {

    private String href;

    private List<T> items;

    private int limit;

    private String next;

    private int total;

}
