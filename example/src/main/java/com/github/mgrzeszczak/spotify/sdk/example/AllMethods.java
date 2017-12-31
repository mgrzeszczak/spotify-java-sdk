package com.github.mgrzeszczak.spotify.sdk.example;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.mgrzeszczak.spotify.sdk.api.SpotifySDK;
import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.AlbumContainer;
import com.github.mgrzeszczak.spotify.sdk.model.AlbumPageContainer;
import com.github.mgrzeszczak.spotify.sdk.model.AlbumSimplifiedPageContainer;
import com.github.mgrzeszczak.spotify.sdk.model.Artist;
import com.github.mgrzeszczak.spotify.sdk.model.ArtistContainer;
import com.github.mgrzeszczak.spotify.sdk.model.ArtistPageContainer;
import com.github.mgrzeszczak.spotify.sdk.model.ArtistsCursorPage;
import com.github.mgrzeszczak.spotify.sdk.model.AudioFeatures;
import com.github.mgrzeszczak.spotify.sdk.model.AudioFeaturesContainer;
import com.github.mgrzeszczak.spotify.sdk.model.Categories;
import com.github.mgrzeszczak.spotify.sdk.model.Category;
import com.github.mgrzeszczak.spotify.sdk.model.CurrentPlayback;
import com.github.mgrzeszczak.spotify.sdk.model.CurrentlyPlaying;
import com.github.mgrzeszczak.spotify.sdk.model.CursorPage;
import com.github.mgrzeszczak.spotify.sdk.model.DeviceContainer;
import com.github.mgrzeszczak.spotify.sdk.model.FeaturedPlaylists;
import com.github.mgrzeszczak.spotify.sdk.model.Image;
import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;
import com.github.mgrzeszczak.spotify.sdk.model.PlayHistory;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistTrack;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistsPageContainer;
import com.github.mgrzeszczak.spotify.sdk.model.Recommendations;
import com.github.mgrzeszczak.spotify.sdk.model.SavedAlbum;
import com.github.mgrzeszczak.spotify.sdk.model.SavedTrack;
import com.github.mgrzeszczak.spotify.sdk.model.Track;
import com.github.mgrzeszczak.spotify.sdk.model.TrackAttributes;
import com.github.mgrzeszczak.spotify.sdk.model.TrackContainer;
import com.github.mgrzeszczak.spotify.sdk.model.TracksPageContainer;
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

        Categories categories = spotify.getCategories(authorization, "pl", "pl", null, null).blockingGet();
        OffsetPage<SavedTrack> savedTracks = spotify.getSavedTracks(authorization, null, null, null).blockingGet();
        OffsetPage<SavedAlbum> savedAlbums = spotify.getSavedAlbums(authorization, null, null, null).blockingGet();

        List<SavedTrack> tracks = savedTracks.getItems();
        SavedTrack track = tracks.get(0);

        Track track2 = spotify.getTrack(authorization, track.getTrack().getId(), null).blockingGet();
        TrackContainer trackContainer = spotify.getTracks(authorization, Collections.singletonList(track2.getId()), null).blockingGet();
        List<Track> tracks2 = trackContainer.getTracks();

        Album album = spotify.getAlbum(authorization, track2.getAlbum().getId(), null).blockingGet();
        AlbumContainer albumContainer = spotify.getAlbums(authorization, Collections.singletonList(album.getId()), null).blockingGet();

        Artist artist = spotify.getArtist(authorization, album.getArtists().get(0).getId()).blockingGet();
        ArtistContainer artistContainer = spotify.getArtists(authorization, Collections.singletonList(artist.getId())).blockingGet();

        OffsetPage<PlaylistSimplified> playlists = spotify.getUserPlaylists(authorization, userPrivate.getId(), null, null).blockingGet();
        ArtistContainer relatedArtists = spotify.getRelatedArtists(authorization, artist.getId()).blockingGet();
        OffsetPage<Track> albumTracks = spotify.getAlbumTracks(authorization, album.getId(), null, null, null).blockingGet();
        OffsetPage<Album> artistAlbums = spotify.getArtistAlbums(authorization, artist.getId(), null, null, null, null).blockingGet();

        Category category = categories.getCategories().getItems().get(0);
        Category category1 = spotify.getCategory(authorization, category.getId(), null, null).blockingGet();

        PlaylistsPageContainer categoryPlaylists = spotify.getCategoryPlaylists(authorization, category.getId(), null, null, null).blockingGet();

        TrackContainer artistsTopTracks = spotify.getArtistTopTracks(authorization, artist.getId(), "PL").blockingGet();
        AudioFeaturesContainer audioFeaturesContainer = spotify.getTracksAudioFeatures(authorization, Collections.singletonList(track2.getId())).blockingGet();
        AudioFeatures audioFeatures = spotify.getTrackAudioFeatures(authorization, track2.getId()).blockingGet();
        Map<String, Object> stringObjectMap = spotify.getTrackAudioAnalysis(authorization, track2.getId()).blockingGet();

        CursorPage<PlayHistory> recentlyPlayed = spotify.getRecentlyPlayed(authorization, null, null, null).blockingGet();
        PlaylistSimplified playlist = playlists.getItems().get(0);
        OffsetPage<PlaylistTrack> playlistTracks = spotify.getPlaylistTracks(authorization, playlist.getOwner().getId(), playlist.getId(), null, null, null, null).blockingGet();
        List<Image> images = spotify.getPlaylistCoverImages(authorization, playlist.getOwner().getId(), playlist.getId()).blockingGet();

        AlbumPageContainer albumPageContainer = spotify.getNewReleases(authorization, null, null, null).blockingGet();
        FeaturedPlaylists featuredPlaylists = spotify.getFeaturedPlaylists(authorization, null, null, null, null, null).blockingGet();
        OffsetPage<Track> trackOffsetPage = spotify.getCurrentUserTopTracks(authorization, null, null, null).blockingGet();
        OffsetPage<Artist> artistOffsetPage = spotify.getCurrentUserTopArtists(authorization, null, null, null).blockingGet();
        OffsetPage<PlaylistSimplified> playlistSimplifiedOffsetPage = spotify.getCurrentUserPlaylists(authorization, null, null).blockingGet();
        ArtistsCursorPage artistsCursorPage = spotify.getCurrentUserFollowedArtists(authorization, null, null).blockingGet();
        Response<DeviceContainer> deviceContainerResponse = spotify.getCurrentUserAvailableDevices(authorization).blockingGet();
        Recommendations recommendations = spotify.getRecommendations(authorization, Collections.singletonList(artist.getId()), null, null, null, null, null, null, TrackAttributes.builder().danceability(1.0f).build()).blockingGet();

        // 34

        // search
        AlbumSimplifiedPageContainer dovydas = spotify.searchAlbums(authorization, "dovydas", null, null, null).blockingGet();
        ArtistPageContainer dovydas1 = spotify.searchArtists(authorization, "dovydas", null, null, null).blockingGet();
        TracksPageContainer dovydas2 = spotify.searchTracks(authorization, "dovydas", null, null, null).blockingGet();
        PlaylistsPageContainer playlistSearchResult = spotify.searchPlaylists(authorization, "dovydas", null, null, null).blockingGet();


        System.out.println(authorization);
        System.out.println("done");
    }

}
