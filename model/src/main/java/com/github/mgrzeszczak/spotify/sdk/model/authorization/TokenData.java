package com.github.mgrzeszczak.spotify.sdk.model.authorization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenData {

    private String accessToken;

    private String refreshToken;

    private int expiresIn;

    private String scope;

    private String tokenType;

}
