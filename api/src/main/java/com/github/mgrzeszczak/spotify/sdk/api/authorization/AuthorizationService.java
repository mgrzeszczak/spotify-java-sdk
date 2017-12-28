package com.github.mgrzeszczak.spotify.sdk.api.authorization;

import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface AuthorizationService {

    String BASE_PATH = "https://accounts.spotify.com/";
    String CODE_GRANT_TYPE = "authorization_code";
    String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";

    @POST("api/token")
    @FormUrlEncoded
    Single<TokenData> getToken(@Field("client_id") String clientId,
                                         @Field("client_secret") String clientSecret,
                                         @Field("grant_type") String grantType,
                                         @Field("code") String code,
                                         @Field("redirect_uri") String redirectUri);

    @POST("api/token")
    @FormUrlEncoded
    Single<TokenData> refreshToken(@Field("client_id") String clientId,
                                             @Field("client_secret") String clientSecret,
                                             @Field("grant_type") String grantType,
                                             @Field("refresh_token") String refreshToken);

}
