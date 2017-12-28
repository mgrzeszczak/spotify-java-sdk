package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.model.Error;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Result<T> {

    private final T body;
    private final Error errorBody;
    private final boolean isSuccessful;


    public Result(T body) {
        this.body = body;
        this.isSuccessful = true;
        this.errorBody = null;
    }

    public Result(Error errorBody) {
        this.errorBody = errorBody;
        this.body = null;
        this.isSuccessful = false;
    }
}
