package com.github.mgrzeszczak.spotify.sdk.model.authorization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthError {

    private String error;

    private String errorDescription;

}
