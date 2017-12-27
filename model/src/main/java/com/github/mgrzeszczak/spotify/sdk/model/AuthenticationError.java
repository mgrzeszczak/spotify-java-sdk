package com.github.mgrzeszczak.spotify.sdk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationError {

    private String error;

    private String errorDescription;

}
