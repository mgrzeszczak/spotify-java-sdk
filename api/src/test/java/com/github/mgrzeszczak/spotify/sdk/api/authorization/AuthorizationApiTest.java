package com.github.mgrzeszczak.spotify.sdk.api.authorization;

import java.util.Arrays;
import java.util.Scanner;

import org.junit.Test;

import com.github.mgrzeszczak.spotify.sdk.model.authorization.Scope;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

public class AuthorizationApiTest {

    private static final String CLIENT_ID = "3e13c3e767024738bb7139ea43301f3d";
    private static final String CLIENT_SECRET = "b1c35cc2ebec440ba96139637ad4ee79";

    @Test
    public void test() throws Exception {
        AuthorizationApi authorizationApi = AuthorizationApi.create(CLIENT_ID, CLIENT_SECRET);
        String redirectUri = "http://localhost/test/";
        /*
        String url = AuthorizationCodeRequestURLBuilder.create()
                .clientId(CLIENT_ID)
                .responseType(AuthorizationCodeRequestURLBuilder.CODE_RESPONSE_TYPE)
                .redirectUri(redirectUri)
                .requestedScopes(Arrays.asList(
                        Scope.STREAMING,
                        Scope.PLAYLIST_MODIFY_PRIVATE
                )).build();
        System.out.println(url);*/

        /*Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();*/

        /*TokenData data = authorizationApi.getToken("AQDg-HCx7QnTWYdHA2Vz-pBEeN9kwhkzNCLP7-Bm2aGF75Ck6wWiJPW6s_q_RtcbA1bFQTJ6p94nCBJIydXcL7PzlZufasb76V8cxN-2LxapSyhRNjZyIcEioShmIpIgN_7M3kGKBNxYMkyykCt0r_1KaUeCgFvJ51Gn2bT-sPPhKi6STAW-R91nDJdhBbpZv1y5Ey1mJzaS9hIdnaHG5nZZemlRHPHPv8GpuAfjecY", redirectUri)
                .blockingGet();*/

        TokenData refreshed = authorizationApi.refreshToken("AQBeh0joNV3w_mII82IkL5Ql_nTJYyvDFtPGKfDLBvOSeYiVZ-JNcHc1pXWJKQvs-u1iCLi8t9miOqshiAFev3TyhxjpYHG7ySxJ9tHjz9ZgOjMTqm3aGyEoBXDmfmTPKvk").blockingGet();

        System.out.println(refreshed);
    }

}