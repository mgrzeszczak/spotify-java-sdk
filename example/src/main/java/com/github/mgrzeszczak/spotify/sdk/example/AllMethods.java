package com.github.mgrzeszczak.spotify.sdk.example;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.mgrzeszczak.spotify.sdk.api.SpotifySDK;
import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.AlbumSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.Artist;
import com.github.mgrzeszczak.spotify.sdk.model.AudioFeatures;
import com.github.mgrzeszczak.spotify.sdk.model.Category;
import com.github.mgrzeszczak.spotify.sdk.model.CurrentPlayback;
import com.github.mgrzeszczak.spotify.sdk.model.CurrentlyPlaying;
import com.github.mgrzeszczak.spotify.sdk.model.CursorPage;
import com.github.mgrzeszczak.spotify.sdk.model.Device;
import com.github.mgrzeszczak.spotify.sdk.model.Devices;
import com.github.mgrzeszczak.spotify.sdk.model.FeaturedPlaylists;
import com.github.mgrzeszczak.spotify.sdk.model.Image;
import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;
import com.github.mgrzeszczak.spotify.sdk.model.PlayHistory;
import com.github.mgrzeszczak.spotify.sdk.model.PlayParameters;
import com.github.mgrzeszczak.spotify.sdk.model.Playlist;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistParameters;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistTrack;
import com.github.mgrzeszczak.spotify.sdk.model.Recommendations;
import com.github.mgrzeszczak.spotify.sdk.model.SavedTrack;
import com.github.mgrzeszczak.spotify.sdk.model.Track;
import com.github.mgrzeszczak.spotify.sdk.model.TrackAttributes;
import com.github.mgrzeszczak.spotify.sdk.model.UserPrivate;
import com.github.mgrzeszczak.spotify.sdk.model.UserPublic;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import retrofit2.Response;

public class AllMethods {

    public static void main(String[] args) throws Exception {
        SpotifyAccess spotifyAccess = AuthorizationProvider.getSpotifyAccess();
        SpotifySDK spotify = spotifyAccess.getSpotify();
        TokenData tokenData = spotifyAccess.getTokenData();
        String authorization = "Bearer " + tokenData.getAccessToken();


        UserPrivate userPrivate = spotify.getCurrentUserProfile(authorization).blockingGet();
        UserPublic userPublic = spotify.getUserProfile(authorization, userPrivate.getId()).blockingGet();

        Response<CurrentPlayback> currentPlaybackResponse = spotify.getCurrentPlayback(authorization, null).blockingGet();
        Response<CurrentlyPlaying> currentlyPlayingResponse = spotify.getCurrentlyPlaying(authorization, null).blockingGet();

        OffsetPage<Category> categories = spotify.getCategories(authorization, "pl", "pl", null, null).blockingGet();
        OffsetPage<SavedTrack> savedTracks = spotify.getSavedTracks(authorization, null, null, null).blockingGet();
        //OffsetPage<SavedAlbum> savedAlbums = spotify.getSavedAlbums(authorization, null, null, null).blockingGet();

        List<SavedTrack> tracks = savedTracks.getItems();
        SavedTrack track = tracks.get(0);

        Track track2 = spotify.getTrack(authorization, track.getTrack().getId(), null).blockingGet();

        Track firstTrack = spotify.getTracks(authorization, Collections.singletonList(track2.getId()), null).blockingFirst();

        Album album = spotify.getAlbum(authorization, track2.getAlbum().getId(), null).blockingGet();
        Album firstAlbum = spotify.getAlbums(authorization, Collections.singletonList(album.getId()), null).blockingFirst();

        Artist artist = spotify.getArtist(authorization, album.getArtists().get(0).getId()).blockingGet();
        Artist firstArtist = spotify.getArtists(authorization, Collections.singletonList(artist.getId())).blockingFirst();

        OffsetPage<PlaylistSimplified> playlists = spotify.getUserPlaylists(authorization, userPrivate.getId(), null, null).blockingGet();
        Artist firstRelatedArtist = spotify.getRelatedArtists(authorization, artist.getId()).blockingFirst();
        OffsetPage<Track> albumTracks = spotify.getAlbumTracks(authorization, album.getId(), null, null, null).blockingGet();
        OffsetPage<Album> artistAlbums = spotify.getArtistAlbums(authorization, artist.getId(), null, null, null, null).blockingGet();

        Category category = categories.getItems().get(0);
        Category category1 = spotify.getCategory(authorization, category.getId(), null, null).blockingGet();

        OffsetPage<PlaylistSimplified> categoryPlaylists = spotify.getCategoryPlaylists(authorization, category.getId(), null, null, null).blockingGet();

        Track firstTopTrack = spotify.getArtistTopTracks(authorization, artist.getId(), "PL").blockingFirst();
        AudioFeatures firstAudioFeatures = spotify.getTracksAudioFeatures(authorization, Collections.singletonList(track2.getId())).blockingFirst();
        AudioFeatures audioFeatures = spotify.getTrackAudioFeatures(authorization, track2.getId()).blockingGet();
        Map<String, Object> stringObjectMap = spotify.getTrackAudioAnalysis(authorization, track2.getId()).blockingGet();

        CursorPage<PlayHistory> recentlyPlayed = spotify.getRecentlyPlayed(authorization, null, null, null).blockingGet();
        PlaylistSimplified playlist = playlists.getItems().get(0);
        OffsetPage<PlaylistTrack> playlistTracks = spotify.getPlaylistTracks(authorization, playlist.getOwner().getId(), playlist.getId(), null, null, null, null).blockingGet();
        List<Image> images = spotify.getPlaylistCoverImages(authorization, playlist.getOwner().getId(), playlist.getId()).blockingGet();

        OffsetPage<Album> newReleases = spotify.getNewReleases(authorization, null, null, null).blockingGet();
        FeaturedPlaylists featuredPlaylists = spotify.getFeaturedPlaylists(authorization, null, null, null, null, null).blockingGet();
        OffsetPage<Track> trackOffsetPage = spotify.getCurrentUserTopTracks(authorization, null, null, null).blockingGet();
        OffsetPage<Artist> artistOffsetPage = spotify.getCurrentUserTopArtists(authorization, null, null, null).blockingGet();
        OffsetPage<PlaylistSimplified> playlistSimplifiedOffsetPage = spotify.getCurrentUserPlaylists(authorization, null, null).blockingGet();
        CursorPage<Artist> followedArtists = spotify.getCurrentUserFollowedArtists(authorization, null, null).blockingGet();
        Response<Devices> deviceContainerResponse = spotify.getCurrentUserAvailableDevices(authorization).blockingGet();
        Recommendations recommendations = spotify.getRecommendations(authorization, Collections.singletonList(artist.getId()), null, null, null, null, null, null, TrackAttributes.builder().danceability(1.0f).build()).blockingGet();

        // 34

        // search
        OffsetPage<AlbumSimplified> albumSearch = spotify.searchAlbums(authorization, "abcd", null, null, null).blockingGet();
        OffsetPage<Artist> artistSearch = spotify.searchArtists(authorization, "abcd", null, null, null).blockingGet();
        OffsetPage<Track> trackSearch = spotify.searchTracks(authorization, "abcd", null, null, null).blockingGet();
        OffsetPage<PlaylistSimplified> playlistSearch = spotify.searchPlaylists(authorization, "abcd", null, null, null).blockingGet();

        Artist firstFollowedArtist = followedArtists.getItems().get(0);

        spotify.unfollowArtists(authorization, Collections.singletonList(firstFollowedArtist.getId())).blockingAwait();
        spotify.followArtists(authorization, Collections.singletonList(firstFollowedArtist.getId())).blockingAwait();
        Device device = deviceContainerResponse.body().getDevices().get(0);

        Response<Void> playResponse = spotify.play(authorization, PlayParameters.builder().uris(Collections.singletonList(track2.getUri())).build(), device.getId()).blockingGet();

        /*spotify.addTracksToPlaylist();
        spotify.updatePlaylistCover();
        spotify.unfollowUsers();
        spotify.unfollowPlaylist();
        spotify.transferPlayback();
        spotify.shuffle();
        spotify.setVolume();
        spotify.setRepeat();
        spotify.seek();
        spotify.saveTracks();
        spotify.saveAlbums();
        spotify.replacePlaylistTracks();
        spotify.reorderPlaylistTracks();
        spotify.removeTracksFromPlaylist();
        spotify.removeTracks();
        spotify.removeAlbums();*/

        tokenData = spotify.refreshToken(tokenData.getRefreshToken()).blockingGet();
        authorization = "Bearer " + tokenData.getAccessToken();

        Playlist newPlaylist = spotify.createPlaylist(
                authorization,
                userPrivate.getId(),
                PlaylistParameters.builder()
                        .isPublic(true)
                        .name("custom playlist2")
                        .collaborative(false)
                        .build()
        ).blockingGet();
        /*spotify.playPreviousTrack();
        spotify.playNextTrack();
        spotify.pause();
        spotify.followUsers();
        spotify.followPlaylist();
        spotify.followArtists();*/

        List<Boolean> tracksContain = spotify.checkSavedTracksContain(authorization, Collections.singletonList(track2.getId())).blockingGet();
        List<Boolean> albumsContain = spotify.checkSavedAlbumsContain(authorization, Collections.singleton(album.getId())).blockingGet();
        List<Boolean> usersFollowPlaylist = spotify.checkIfUsersFollowPlaylist(authorization, playlist.getOwner().getId(), playlist.getId(), Collections.singleton(userPrivate.getId())).blockingGet();
        List<Boolean> currentUserFollowsArtists = spotify.checkIfCurrentUserFollowsArtists(authorization, Collections.singletonList(artist.getId())).blockingGet();
        List<Boolean> currentUserFollowsUsers = spotify.checkIfCurrentUserFollowsUsers(authorization, Collections.singletonList(userPrivate.getId())).blockingGet();

        PlaylistParameters params = PlaylistParameters.builder().name("new name2").description("description").build();
        spotify.changePlaylistDetails(authorization, newPlaylist.getOwner().getId(), newPlaylist.getId(), params).blockingAwait();

        System.out.println(authorization);
        System.out.println("done");
    }

}
