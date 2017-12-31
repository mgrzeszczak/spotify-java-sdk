package com.github.mgrzeszczak.spotify.sdk.api;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Wrapper<T> {

    private T data;

    @JsonAnySetter
    public void set(String code, T data) {
        this.data = data;
    }

}
