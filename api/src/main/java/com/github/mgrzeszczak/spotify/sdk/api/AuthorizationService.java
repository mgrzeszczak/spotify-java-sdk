package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

interface AuthorizationService {

    String URL = "https://accounts.spotify.com/api/token";
    String CODE_GRANT_TYPE = "authorization_code";
    String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";

    @POST
    @FormUrlEncoded
    Single<TokenData> getToken(@Url String url,
                               @Field("client_id") String clientId,
                               @Field("client_secret") String clientSecret,
                               @Field("grant_type") String grantType,
                               @Field("code") String code,
                               @Field("redirect_uri") String redirectUri);

    @POST
    @FormUrlEncoded
    Single<TokenData> refreshToken(@Url String url,
                                   @Field("client_id") String clientId,
                                   @Field("client_secret") String clientSecret,
                                   @Field("grant_type") String grantType,
                                   @Field("refresh_token") String refreshToken);

}
