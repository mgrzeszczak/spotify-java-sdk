package com.github.mgrzeszczak.spotify.sdk.api;

import com.github.mgrzeszczak.spotify.sdk.model.UserPrivate;
import com.github.mgrzeszczak.spotify.sdk.model.UserPublic;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

interface UserService {

    @GET("v1/me")
    Single<UserPrivate> getCurrentUserProfile(@Header("Authorization") String authorization);

    @GET("v1/users/{user_id}")
    Single<UserPublic> getUserProfile(@Header("Authorization") String authorization,
                                      @Path("user_id") String userId);

}
