package com.github.mgrzeszczak.spotify.sdk.example;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import io.reactivex.functions.Consumer;

public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class);


    public static void main(String[] args) throws Exception {
        SpotifyAccess spotifyAccess = AuthorizationProvider.getSpotifyAccess();
        SpotifySDK spotify = spotifyAccess.getSpotify();
        TokenData tokenData = spotifyAccess.getTokenData();
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


}
