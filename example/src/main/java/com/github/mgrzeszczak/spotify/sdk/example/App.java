package com.github.mgrzeszczak.spotify.sdk.example;

import static com.github.mgrzeszczak.spotify.sdk.example.Utils.interceptRedirect;
import static com.github.mgrzeszczak.spotify.sdk.example.Utils.readFile;

import java.awt.*;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.mgrzeszczak.spotify.sdk.api.SpotifyAuthCodeURLRequestBuilder;
import com.github.mgrzeszczak.spotify.sdk.api.SpotifySDK;
import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.Artist;
import com.github.mgrzeszczak.spotify.sdk.model.ArtistContainer;
import com.github.mgrzeszczak.spotify.sdk.model.ArtistSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.ArtistsCursorPage;
import com.github.mgrzeszczak.spotify.sdk.model.CursorPage;
import com.github.mgrzeszczak.spotify.sdk.model.PlayParameters;
import com.github.mgrzeszczak.spotify.sdk.model.Recommendations;
import com.github.mgrzeszczak.spotify.sdk.model.TrackAttributes;
import com.github.mgrzeszczak.spotify.sdk.model.UserPrivate;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.Scope;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class);
    private static final int PORT = 8080;
    private static final String ADDRESS = "0.0.0.0";
    private static final String REDIRECT_URI = "http://localhost:" + PORT + "/redirect";

    public static void main(String[] args) throws Exception {
        String clientId = readFile("spotify-client-id");
        String clientSecret = readFile("spotify-client-secret");

        String authorizationCode = getAuthorizationCode(clientId);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        // logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        SpotifySDK spotify = SpotifySDK.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .okHttpClient(okHttpClient)
                .build();

        TokenData tokenData = spotify.getToken(authorizationCode, REDIRECT_URI).blockingGet();
        String authorization = "Bearer " + tokenData.getAccessToken();

        spotify.playNextTrack(authorization, null).subscribe(r -> LOGGER.info(r.code()));
        spotify.playPreviousTrack(authorization, null).subscribe(r -> LOGGER.info(r.code()));
        spotify.playPreviousTrack(authorization, null).subscribe(r -> LOGGER.info(r.code()));
        spotify.setVolume(authorization, 10, null).subscribe(r -> LOGGER.info(r.code()));
        spotify.setVolume(authorization, 100, null).subscribe(r -> LOGGER.info(r.code()));

        spotify.getCurrentlyPlaying(authorization, null).subscribe(r -> LOGGER.info(r.body()));
        spotify.getCurrentPlayback(authorization, null).subscribe(r -> LOGGER.info(r.body()));

        spotify.play(
                authorization,
                PlayParameters.builder()
                        .contextUri("spotify:album:1tzrwGajPhpdX2EVkCnmHZ")
                        .offset(PlayParameters.Offset.builder().position(4).build())
                        .build(),
                null).subscribe(r -> LOGGER.info(r));

        Album album = spotify.getAlbum(authorization, "1tzrwGajPhpdX2EVkCnmHZ", null).blockingGet();
        LOGGER.info(album);

        ArtistSimplified artistSimple = album.getArtists().get(0);
        LOGGER.info(artistSimple);

        Artist artist = spotify.getArtist(authorization, artistSimple.getId()).blockingGet();
        LOGGER.info(artist);

        spotify.getRelatedArtists(authorization, artist.getId())
                .subscribe((Consumer<ArtistContainer>) LOGGER::info);

        Recommendations recommendations = spotify.getRecommendations(
                authorization,
                Collections.singletonList(artist.getId()),
                null,
                null,
                null,
                null,
                null,
                null,
                TrackAttributes.builder().danceability(1.0f).build()
        ).blockingGet();
        LOGGER.info(recommendations);

        spotify.getCurrentUserProfile(authorization).subscribe((Consumer<UserPrivate>) LOGGER::info);
        ArtistsCursorPage artistsCursorPage = spotify.getCurrentUserFollowedArtists(authorization, null, null).blockingGet();
        CursorPage<Artist> page = artistsCursorPage.getArtists();
        List<Artist> items = page.getItems();
        if (items.size() > 0) {
            Artist followedArtist = items.get(0);
            LOGGER.info("Unfollowing " + artist.getName());
            spotify.unfollowArtists(authorization, Collections.singletonList(followedArtist.getId()))
                    .subscribe(() -> LOGGER.info("Unfollowed!"));
        }
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
