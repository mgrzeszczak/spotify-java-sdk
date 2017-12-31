package com.github.mgrzeszczak.spotify.sdk.example;

import static com.github.mgrzeszczak.spotify.sdk.example.Utils.interceptRedirect;
import static com.github.mgrzeszczak.spotify.sdk.example.Utils.readFile;

import java.awt.*;
import java.net.URI;
import java.util.Arrays;

import com.github.mgrzeszczak.spotify.sdk.api.SpotifyAuthCodeURLRequestBuilder;
import com.github.mgrzeszczak.spotify.sdk.api.SpotifySDK;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.Scope;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

class AuthorizationProvider {

    private static final int PORT = 8080;
    private static final String ADDRESS = "0.0.0.0";
    private static final String REDIRECT_URI = "http://localhost:" + PORT + "/redirect";

    private AuthorizationProvider() {
        throw new AssertionError("no instances");
    }

    static SpotifyAccess getSpotifyAccess() throws Exception {
        String clientId = readFile("spotify-client-id");
        String clientSecret = readFile("spotify-client-secret");

        String authorizationCode = getAuthorizationCode(clientId);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        SpotifySDK spotify = SpotifySDK.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .okHttpClient(okHttpClient)
                .build();

        TokenData tokenData = spotify.getToken(authorizationCode, REDIRECT_URI).blockingGet();
        return new SpotifyAccess(spotify, tokenData);
    }

    private static String getAuthorizationCode(String clientId) throws Exception {
        String state = "userId";

        String url = SpotifyAuthCodeURLRequestBuilder.create()
                .clientId(clientId)
                .responseType(SpotifyAuthCodeURLRequestBuilder.CODE_RESPONSE_TYPE)
                .redirectUri(REDIRECT_URI)
                .state(state)
                .requestedScopes(Arrays.asList(Scope.values()))
                .build();

        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            throw new RuntimeException("cannot open browser");
        }
        Desktop.getDesktop().browse(new URI(url));
        AuthRedirect response = interceptRedirect(ADDRESS, PORT);
        return response.getAuthorizationCode();
    }

}
