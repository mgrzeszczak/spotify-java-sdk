package com.github.mgrzeszczak.spotify.sdk.api;

import static com.github.mgrzeszczak.spotify.sdk.api.Utils.commaJoin;
import static com.github.mgrzeszczak.spotify.sdk.api.Utils.requireNonNull;
import static com.github.mgrzeszczak.spotify.sdk.api.Utils.toSnakeCaseMap;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.github.mgrzeszczak.spotify.sdk.api.annotation.Beta;
import com.github.mgrzeszczak.spotify.sdk.api.annotation.RequiredScope;
import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.AlbumContainer;
import com.github.mgrzeszczak.spotify.sdk.model.AlbumSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.Artist;
import com.github.mgrzeszczak.spotify.sdk.model.ArtistContainer;
import com.github.mgrzeszczak.spotify.sdk.model.ArtistsCursorPage;
import com.github.mgrzeszczak.spotify.sdk.model.AudioFeatures;
import com.github.mgrzeszczak.spotify.sdk.model.AudioFeaturesContainer;
import com.github.mgrzeszczak.spotify.sdk.model.Category;
import com.github.mgrzeszczak.spotify.sdk.model.CurrentPlayback;
import com.github.mgrzeszczak.spotify.sdk.model.CurrentlyPlaying;
import com.github.mgrzeszczak.spotify.sdk.model.CursorPage;
import com.github.mgrzeszczak.spotify.sdk.model.DeviceContainer;
import com.github.mgrzeszczak.spotify.sdk.model.ErrorHolder;
import com.github.mgrzeszczak.spotify.sdk.model.Image;
import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;
import com.github.mgrzeszczak.spotify.sdk.model.PlayHistory;
import com.github.mgrzeszczak.spotify.sdk.model.PlayParameters;
import com.github.mgrzeszczak.spotify.sdk.model.Playlist;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistParameters;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistTrack;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistTrackRemovalParameters;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistTrackReorderParameters;
import com.github.mgrzeszczak.spotify.sdk.model.Recommendations;
import com.github.mgrzeszczak.spotify.sdk.model.SavedAlbum;
import com.github.mgrzeszczak.spotify.sdk.model.SavedTrack;
import com.github.mgrzeszczak.spotify.sdk.model.SnapshotIdContainer;
import com.github.mgrzeszczak.spotify.sdk.model.Track;
import com.github.mgrzeszczak.spotify.sdk.model.TrackAttributes;
import com.github.mgrzeszczak.spotify.sdk.model.TrackContainer;
import com.github.mgrzeszczak.spotify.sdk.model.TransferPlaybackParameters;
import com.github.mgrzeszczak.spotify.sdk.model.UserPrivate;
import com.github.mgrzeszczak.spotify.sdk.model.UserPublic;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.AuthError;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.Scope;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@SuppressWarnings("unused")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class SpotifySDK {

    private static final SpotifyObjectMapper MAPPER = new SpotifyObjectMapper();

    private final String clientId;
    private final String clientSecret;
    private final AuthorizationService authorizationService;

    private final AlbumService albumService;
    private final ArtistService artistService;
    private final BrowseService browseService;
    private final UserService userService;
    private final SearchService searchService;
    private final FollowService followService;
    private final PersonalizationService personalizationService;
    private final TrackService trackService;
    private final PlayerService playerService;
    private final PlaylistService playlistService;
    private final LibraryService libraryService;

    private final RxJavaExceptionConverter apiExceptionConverter;
    private final RxJavaExceptionConverter authExceptionConverter;

    public Single<TokenData> getToken(@NotNull String authorizationCode, @NotNull String redirectUri) {
        requireNonNull(authorizationCode, redirectUri);
        return authorizationService.getToken(
                AuthorizationService.URL,
                clientId,
                clientSecret,
                AuthorizationService.CODE_GRANT_TYPE,
                authorizationCode,
                redirectUri
        ).onErrorResumeNext(authExceptionConverter::convertSingle);
    }

    public Single<TokenData> refreshToken(@NotNull String refreshToken) {
        requireNonNull(refreshToken);
        return authorizationService.refreshToken(
                AuthorizationService.URL,
                clientId,
                clientSecret,
                AuthorizationService.REFRESH_TOKEN_GRANT_TYPE,
                refreshToken
        ).onErrorResumeNext(authExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<Album> getAlbum(@NotNull String authorization,
                                  @NotNull String albumId,
                                  @Nullable String market) {
        requireNonNull(authorization, albumId);
        return albumService.getAlbum(
                authorization,
                albumId,
                market
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<AlbumContainer> getAlbums(@NotNull String authorization,
                                            @NotNull Collection<String> albumIds,
                                            @Nullable String market) {
        requireNonNull(authorization, albumIds);
        return albumService.getAlbums(authorization, commaJoin(albumIds), market)
                .onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<OffsetPage<Track>> getAlbumTracks(@NotNull String authorization,
                                                    @NotNull String albumId,
                                                    @Nullable Integer limit,
                                                    @Nullable Integer offset,
                                                    @Nullable String market) {
        requireNonNull(authorization, albumId);
        return albumService.getAlbumTracks(
                authorization,
                albumId,
                limit,
                offset,
                market
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<Artist> getArtist(@NotNull String authorization,
                                    @NotNull String artistId) {
        requireNonNull(authorization, artistId);
        return artistService.getArtist(
                authorization,
                artistId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<ArtistContainer> getArtists(@NotNull String authorization,
                                              @NotNull Collection<String> artistIds) {
        requireNonNull(authorization, artistIds);
        return artistService.getArtists(
                authorization,
                commaJoin(artistIds)
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<ArtistContainer> getRelatedArtists(@NotNull String authorization,
                                                     @NotNull String artistId) {
        requireNonNull(authorization, artistId);
        return artistService.getRelatedArtists(authorization, artistId)
                .onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<TrackContainer> getArtistTopTracks(@NotNull String authorization,
                                                     @NotNull String artistId,
                                                     @NotNull String country) {
        requireNonNull(authorization, artistId, country);
        return artistService.getArtistTopTracks(authorization, artistId, country)
                .onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<OffsetPage<Album>> getArtistAlbums(@NotNull String authorization,
                                                     @NotNull String artistId,
                                                     @Nullable Collection<String> albumTypes,
                                                     @Nullable String market,
                                                     @Nullable Integer limit,
                                                     @Nullable Integer offset) {
        requireNonNull(authorization, artistId);
        return artistService.getArtistAlbums(
                authorization,
                artistId,
                commaJoin(albumTypes),
                market,
                limit,
                offset
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<Category> getCategory(@NotNull String authorization,
                                        @NotNull String categoryId,
                                        @Nullable String country,
                                        @Nullable String locale) {
        requireNonNull(authorization, categoryId);
        return browseService.getCategory(
                authorization,
                categoryId,
                country,
                locale
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<OffsetPage<PlaylistSimplified>> getCategoryPlaylists(@NotNull String authorization,
                                                                       @NotNull String categoryId,
                                                                       @Nullable String country,
                                                                       @Nullable Integer limit,
                                                                       @Nullable Integer offset) {
        requireNonNull(authorization, categoryId);
        return browseService.getCategoryPlaylists(
                authorization,
                categoryId,
                country,
                limit,
                offset
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<OffsetPage<Category>> getCategories(@NotNull String authorization,
                                                      @Nullable String locale,
                                                      @Nullable String country,
                                                      @Nullable Integer limit,
                                                      @Nullable Integer offset) {
        requireNonNull(authorization);
        return browseService.getCategories(
                authorization,
                locale,
                country,
                limit,
                offset
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<OffsetPage<PlaylistSimplified>> getFeaturedPlaylists(@NotNull String authorization,
                                                                       @Nullable String locale,
                                                                       @Nullable String country,
                                                                       @Nullable String timestamp,
                                                                       @Nullable Integer limit,
                                                                       @Nullable Integer offset) {
        requireNonNull(authorization);
        return browseService.getFeaturedPlaylists(
                authorization,
                locale,
                country,
                timestamp,
                limit,
                offset
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<OffsetPage<AlbumSimplified>> getNewReleases(@NotNull String authorization,
                                                              @Nullable String country,
                                                              @Nullable Integer limit,
                                                              @Nullable Integer offset) {
        requireNonNull(authorization);
        return browseService.getNewReleases(
                authorization,
                country,
                limit,
                offset
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<Recommendations> getRecommendations(@NotNull String authorization,
                                                      @Nullable List<String> seedArtists,
                                                      @Nullable List<String> seedGenres,
                                                      @Nullable List<String> seedTracks,
                                                      @Nullable String limit,
                                                      @Nullable String market,
                                                      @Nullable TrackAttributes minTrackAttributes,
                                                      @Nullable TrackAttributes maxTrackAttributes,
                                                      @Nullable TrackAttributes targetTrackAttributes) {
        requireNonNull(authorization);
        return browseService.getRecommendations(
                authorization,
                commaJoin(seedArtists),
                commaJoin(seedGenres),
                commaJoin(seedTracks),
                limit,
                market,
                toSnakeCaseMap(minTrackAttributes, "min_"),
                toSnakeCaseMap(maxTrackAttributes, "max_"),
                toSnakeCaseMap(targetTrackAttributes, "target_")
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<OffsetPage<Artist>> searchArtists(@NotNull String authorization,
                                                    @NotNull String query,
                                                    @Nullable String market,
                                                    @Nullable Integer limit,
                                                    @Nullable Integer offset) {
        requireNonNull(authorization, query);
        return searchService.searchArtists(
                authorization,
                query,
                "artist",
                market,
                limit,
                offset
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<OffsetPage<AlbumSimplified>> searchAlbums(@NotNull String authorization,
                                                            @NotNull String query,
                                                            @Nullable String market,
                                                            @Nullable Integer limit,
                                                            @Nullable Integer offset) {
        requireNonNull(authorization, query);
        return searchService.searchAlbums(
                authorization,
                query,
                "album",
                market,
                limit,
                offset
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<OffsetPage<Track>> searchTracks(@NotNull String authorization,
                                                  @NotNull String query,
                                                  @Nullable String market,
                                                  @Nullable Integer limit,
                                                  @Nullable Integer offset) {
        requireNonNull(authorization, query);
        return searchService.searchTracks(
                authorization,
                query,
                "track",
                market,
                limit,
                offset
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<OffsetPage<PlaylistSimplified>> searchPlaylists(@NotNull String authorization,
                                                                  @NotNull String query,
                                                                  @Nullable String market,
                                                                  @Nullable Integer limit,
                                                                  @Nullable Integer offset) {
        requireNonNull(authorization, query);
        return searchService.searchPlaylists(
                authorization,
                query,
                "playlist",
                market,
                limit,
                offset
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.USER_READ_PRIVATE,
            Scope.USER_READ_EMAIL,
            Scope.USER_READ_BIRTHDATE
    })
    public Single<UserPrivate> getCurrentUserProfile(@NotNull String authorization) {
        requireNonNull(authorization);
        return userService.getCurrentUserProfile(authorization)
                .onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<UserPublic> getUserProfile(@NotNull String authorization,
                                             @NotNull String userId) {
        requireNonNull(authorization, userId);
        return userService.getUserProfile(authorization, userId)
                .onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.USER_FOLLOW_READ
    })
    public Single<List<Boolean>> checkIfCurrentUserFollows(@NotNull String authorization,
                                                           @NotNull String type,
                                                           @NotNull List<String> ids) {
        requireNonNull(authorization, type, ids);
        return followService.checkIfCurrentUserFollows(
                authorization,
                type,
                commaJoin(ids)
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<List<Boolean>> checkIfUsersFollowPlaylist(@NotNull String authorization,
                                                            @NotNull String ownerId,
                                                            @NotNull String playlistId,
                                                            @NotNull List<String> userIds) {
        requireNonNull(authorization, ownerId, playlistId, userIds);
        return followService.checkIfUsersFollowPlaylist(
                authorization,
                ownerId,
                playlistId,
                commaJoin(userIds)
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.USER_FOLLOW_MODIFY
    })
    public Completable followArtistOrUser(@NotNull String authorization,
                                          @NotNull String type,
                                          @NotNull List<String> ids) {
        requireNonNull(authorization, type, ids);
        return followService.followArtistOrUser(
                authorization,
                type,
                commaJoin(ids)
        ).onErrorResumeNext(apiExceptionConverter::convertCompletable);
    }

    @RequiredScope({
            Scope.USER_FOLLOW_MODIFY
    })
    public Completable unfollowArtistOrUser(@NotNull String authorization,
                                            @NotNull String type,
                                            @NotNull List<String> ids) {
        requireNonNull(authorization, type, ids);
        return followService.unfollowArtistOrUser(
                authorization,
                type,
                commaJoin(ids)
        ).onErrorResumeNext(apiExceptionConverter::convertCompletable);
    }

    @RequiredScope({
            Scope.PLAYLIST_MODIFY_PUBLIC,
            Scope.PLAYLIST_MODIFY_PRIVATE
    })
    public Completable followPlaylist(@NotNull String authorization,
                                      @NotNull String ownerId,
                                      @NotNull String playlistId,
                                      boolean includeInPublicPlaylists) {
        requireNonNull(authorization, ownerId, playlistId);
        return followService.followPlaylist(
                authorization,
                ownerId,
                playlistId,
                includeInPublicPlaylists
        ).onErrorResumeNext(apiExceptionConverter::convertCompletable);
    }

    @RequiredScope({
            Scope.PLAYLIST_MODIFY_PUBLIC,
            Scope.PLAYLIST_MODIFY_PRIVATE
    })
    public Completable unfollowPlaylist(@NotNull String authorization,
                                        @NotNull String ownerId,
                                        @NotNull String playlistId) {
        requireNonNull(authorization, ownerId, playlistId);
        return followService.unfollowPlaylist(
                authorization,
                ownerId,
                playlistId
        ).onErrorResumeNext(apiExceptionConverter::convertCompletable);
    }

    @RequiredScope({
            Scope.USER_FOLLOW_READ
    })
    public Single<ArtistsCursorPage> getFollowedArtists(@NotNull String authorization,
                                                        @Nullable String limit,
                                                        @Nullable String after) {
        requireNonNull(authorization);
        return followService.getFollowedArtists(
                authorization,
                "artist",
                limit,
                after
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.USER_TOP_READ
    })
    public Single<OffsetPage<Artist>> getCurrentUserTopArtists(@NotNull String authorization,
                                                               @Nullable Integer limit,
                                                               @Nullable Integer offset,
                                                               @Nullable String timeRange) {
        requireNonNull(authorization);
        return personalizationService.getCurrentUserTopArtists(
                authorization,
                "artists",
                limit,
                offset,
                timeRange
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.USER_TOP_READ
    })
    public Single<OffsetPage<Track>> getCurrentUserTopTracks(@NotNull String authorization,
                                                             @Nullable Integer limit,
                                                             @Nullable Integer offset,
                                                             @Nullable String timeRange) {
        requireNonNull(authorization);
        return personalizationService.getCurrentUserTopTracks(
                authorization,
                "tracks",
                limit,
                offset,
                timeRange
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<Track> getTrack(@NotNull String authorization,
                                  @NotNull String trackId,
                                  @Nullable String market) {
        requireNonNull(authorization, trackId);
        return trackService.getTrack(
                authorization,
                trackId,
                market
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<TrackContainer> getTracks(@NotNull String authorization,
                                            @NotNull Collection<String> trackIds,
                                            @Nullable String market) {
        requireNonNull(authorization, trackIds);
        return trackService.getTracks(
                authorization,
                commaJoin(trackIds),
                market
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<AudioFeatures> getTrackAudioFeatures(@NotNull String authorization,
                                                       @NotNull String trackId) {
        requireNonNull(authorization, trackId);
        return trackService.getTrackAudioFeatures(
                authorization,
                trackId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<AudioFeaturesContainer> getTracksAudioFeatures(@NotNull String authorization,
                                                                 @NotNull Collection<String> trackIds) {
        requireNonNull(authorization, trackIds);
        return trackService.getTracksAudioFeatures(
                authorization,
                commaJoin(trackIds)
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<Map<String, Object>> getTrackAudioAnalysis(@NotNull String authorization,
                                                             @NotNull String trackId) {
        requireNonNull(authorization, trackId);
        return trackService.getTrackAudioAnalysis(
                authorization,
                trackId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_READ_RECENTLY_PLAYED
    })
    public Single<CursorPage<PlayHistory>> getRecentlyPlayed(@NotNull String authorization,
                                                             @Nullable Integer limit,
                                                             @Nullable Long after,
                                                             @Nullable Long before) {
        requireNonNull(authorization);
        return playerService.getRecentlyPlayed(
                authorization,
                limit,
                after,
                before
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_READ_PLAYBACK_STATE
    })
    public Single<Response<DeviceContainer>> getUserAvailableDevices(@NotNull String authorization) {
        requireNonNull(authorization);
        return playerService.getUserAvailableDevices(
                authorization
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_MODIFY_PLAYBACK_STATE
    })
    public Single<Response<Void>> next(@NotNull String authorization,
                                       @Nullable String deviceId) {
        requireNonNull(authorization);
        return playerService.next(
                authorization,
                deviceId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_MODIFY_PLAYBACK_STATE
    })
    public Single<Response<Void>> previous(@NotNull String authorization,
                                           @Nullable String deviceId) {
        requireNonNull(authorization);
        return playerService.next(
                authorization,
                deviceId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_READ_CURRENTLY_PLAYING,
            Scope.USER_READ_PLAYBACK_STATE
    })
    public Single<Response<CurrentlyPlaying>> getCurrentlyPlaying(@NotNull String authorization,
                                                                  @Nullable String market) {
        requireNonNull(authorization);
        return playerService.getCurrentlyPlaying(
                authorization,
                market
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_READ_PLAYBACK_STATE
    })
    public Single<Response<CurrentPlayback>> getCurrentPlayback(@NotNull String authorization,
                                                                @Nullable String market) {
        requireNonNull(authorization);
        return playerService.getCurrentPlayback(
                authorization,
                market
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_MODIFY_PLAYBACK_STATE
    })
    public Single<Response<Void>> pause(@NotNull String authorization,
                                        @Nullable String deviceId) {
        requireNonNull(authorization);
        return playerService.pause(
                authorization,
                deviceId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_MODIFY_PLAYBACK_STATE
    })
    public Single<Response<Void>> seek(@NotNull String authorization,
                                       long positionMs,
                                       @Nullable String deviceId) {
        requireNonNull(authorization);
        return playerService.seek(
                authorization,
                positionMs,
                deviceId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_MODIFY_PLAYBACK_STATE
    })
    public Single<Response<Void>> repeat(@NotNull String authorization,
                                         @NotNull String state,
                                         @Nullable String deviceId) {
        requireNonNull(authorization);
        return playerService.repeat(
                authorization,
                state,
                deviceId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_MODIFY_PLAYBACK_STATE
    })
    public Single<Response<Void>> volume(@NotNull String authorization,
                                         int volumePercent,
                                         @Nullable String deviceId) {
        requireNonNull(authorization);
        return playerService.volume(
                authorization,
                volumePercent,
                deviceId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_MODIFY_PLAYBACK_STATE
    })
    public Single<Response<Void>> play(@NotNull String authorization,
                                       @NotNull PlayParameters playParameters,
                                       @Nullable String deviceId) {
        requireNonNull(authorization, playParameters);
        return playerService.play(
                authorization,
                deviceId,
                playParameters
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_MODIFY_PLAYBACK_STATE
    })
    public Single<Response<Void>> shuffle(@NotNull String authorization,
                                          boolean state,
                                          @Nullable String deviceId) {
        requireNonNull(authorization);
        return playerService.shuffle(
                authorization,
                state,
                deviceId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @Beta
    @RequiredScope({
            Scope.USER_MODIFY_PLAYBACK_STATE
    })
    public Single<Response<Void>> transferPlayback(@NotNull String authorization,
                                                   @NotNull TransferPlaybackParameters transferPlaybackParameters) {
        requireNonNull(authorization, transferPlaybackParameters);
        return playerService.transferPlayback(
                authorization,
                transferPlaybackParameters
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.PLAYLIST_MODIFY_PRIVATE,
            Scope.PLAYLIST_MODIFY_PUBLIC
    })
    public Single<SnapshotIdContainer> addTracksToPlaylist(@NotNull String authorization,
                                                           @NotNull String userId,
                                                           @NotNull String playlistId,
                                                           @NotNull Collection<String> trackUris,
                                                           @Nullable Integer position) {
        requireNonNull(authorization, userId, playlistId, trackUris);
        return playlistService.addTracksToPlaylist(
                authorization,
                userId,
                playlistId,
                commaJoin(trackUris),
                position
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.PLAYLIST_MODIFY_PRIVATE,
            Scope.PLAYLIST_MODIFY_PUBLIC
    })
    public Completable changePlaylistDetails(@NotNull String authorization,
                                             @NotNull String userId,
                                             @NotNull String playlistId,
                                             @NotNull PlaylistParameters playlistParameters) {
        requireNonNull(authorization, userId, playlistId, playlistParameters);
        return playlistService.changePlaylistDetails(
                authorization,
                userId,
                playlistId,
                playlistParameters
        ).onErrorResumeNext(apiExceptionConverter::convertCompletable);
    }

    @RequiredScope({
            Scope.PLAYLIST_MODIFY_PRIVATE,
            Scope.PLAYLIST_MODIFY_PUBLIC
    })
    public Single<Playlist> createPlaylist(@NotNull String authorization,
                                           @NotNull String userId,
                                           @NotNull PlaylistParameters playlistParameters) {
        requireNonNull(authorization, userId, playlistParameters);
        return playlistService.createPlaylist(
                authorization,
                userId,
                playlistParameters
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.PLAYLIST_READ_PRIVATE,
            Scope.PLAYLIST_READ_COLLABORATIVE
    })
    public Single<OffsetPage<PlaylistSimplified>> getCurrentUserPlaylists(@NotNull String authorization,
                                                                          @Nullable Integer limit,
                                                                          @Nullable Integer offset) {
        requireNonNull(authorization);
        return playlistService.getCurrentUserPlaylists(
                authorization,
                limit,
                offset
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.PLAYLIST_READ_PRIVATE,
            Scope.PLAYLIST_READ_COLLABORATIVE
    })
    public Single<OffsetPage<PlaylistSimplified>> getUserPlaylists(@NotNull String authorization,
                                                                   @NotNull String userId,
                                                                   @Nullable Integer limit,
                                                                   @Nullable Integer offset) {
        requireNonNull(authorization, userId);
        return playlistService.getUserPlaylists(
                authorization,
                userId,
                limit,
                offset
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<List<Image>> getPlaylistCoverImages(@NotNull String authorization,
                                                      @NotNull String userId,
                                                      @NotNull String playlistId) {
        requireNonNull(authorization, userId, playlistId);
        return playlistService.getPlaylistCoverImages(
                authorization,
                userId,
                playlistId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<Playlist> getPlaylist(@NotNull String authorization,
                                        @NotNull String userId,
                                        @NotNull String playlistId,
                                        @Nullable Collection<String> fields,
                                        @Nullable String market) {
        requireNonNull(authorization, userId, playlistId);
        return playlistService.getPlaylist(
                authorization,
                userId,
                playlistId,
                commaJoin(fields),
                market
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({})
    public Single<OffsetPage<PlaylistTrack>> getPlaylistTracks(@NotNull String authorization,
                                                               @NotNull String userId,
                                                               @NotNull String playlistId,
                                                               @Nullable Collection<String> fields,
                                                               @Nullable Integer limit,
                                                               @Nullable Integer offset,
                                                               @Nullable String market) {
        requireNonNull(authorization, userId, playlistId);
        return playlistService.getPlaylistTracks(
                authorization,
                userId,
                playlistId,
                commaJoin(fields),
                limit,
                offset,
                market
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.PLAYLIST_MODIFY_PRIVATE,
            Scope.PLAYLIST_MODIFY_PUBLIC
    })
    public Single<SnapshotIdContainer> removeTracksFromPlaylist(@NotNull String authorization,
                                                                @NotNull String userId,
                                                                @NotNull String playlistId,
                                                                @NotNull PlaylistTrackRemovalParameters parameters) {
        requireNonNull(authorization, userId, playlistId, parameters);
        return playlistService.removeTracksFromPlaylist(
                authorization,
                userId,
                playlistId,
                parameters
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.PLAYLIST_MODIFY_PRIVATE,
            Scope.PLAYLIST_MODIFY_PUBLIC
    })
    public Single<SnapshotIdContainer> reorderPlaylistTracks(@NotNull String authorization,
                                                             @NotNull String userId,
                                                             @NotNull String playlistId,
                                                             @NotNull PlaylistTrackReorderParameters parameters) {
        requireNonNull(authorization, userId, playlistId, parameters);
        return playlistService.reorderPlaylistTracks(
                authorization,
                userId,
                playlistId,
                parameters
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.PLAYLIST_MODIFY_PRIVATE,
            Scope.PLAYLIST_MODIFY_PUBLIC
    })
    public Completable replacePlaylistTracks(@NotNull String authorization,
                                             @NotNull String userId,
                                             @NotNull String playlistId,
                                             @NotNull Collection<String> trackUris) {
        requireNonNull(authorization, userId, playlistId, trackUris);
        return playlistService.replacePlaylistTracks(
                authorization,
                userId,
                playlistId,
                commaJoin(trackUris)
        ).onErrorResumeNext(apiExceptionConverter::convertCompletable);
    }

    @RequiredScope({
            Scope.PLAYLIST_MODIFY_PRIVATE,
            Scope.PLAYLIST_MODIFY_PUBLIC,
            Scope.UGC_IMAGE_UPLOAD
    })
    public Completable updatePlaylistCover(@NotNull String authorization,
                                           @NotNull String userId,
                                           @NotNull String playlistId,
                                           @NotNull String base64JpegImage) {
        requireNonNull(authorization, userId, playlistId, base64JpegImage);
        return playlistService.updatePlaylistCover(
                authorization,
                userId,
                playlistId,
                base64JpegImage
        ).onErrorResumeNext(apiExceptionConverter::convertCompletable);
    }

    @RequiredScope({
            Scope.USER_LIBRARY_READ
    })
    public Single<List<Boolean>> checkSavedAlbumsContain(@NotNull String authorization,
                                                         @NotNull Collection<String> albumIds) {
        requireNonNull(authorization, albumIds);
        return libraryService.checkCurrentUserSavedAlbums(
                authorization,
                commaJoin(albumIds)
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.USER_LIBRARY_READ
    })
    public Single<List<Boolean>> checkSavedTracksContain(@NotNull String authorization,
                                                         @NotNull Collection<String> trackIds) {
        requireNonNull(authorization, trackIds);
        return libraryService.checkCurrentUserSavedTracks(
                authorization,
                commaJoin(trackIds)
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.USER_LIBRARY_READ
    })
    public Single<OffsetPage<SavedAlbum>> getSavedAlbums(@NotNull String authorization,
                                                         @Nullable Integer limit,
                                                         @Nullable Integer offset,
                                                         @Nullable String market) {
        requireNonNull(authorization);
        return libraryService.getSavedAlbums(
                authorization,
                limit,
                offset,
                market
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.USER_LIBRARY_READ
    })
    public Single<OffsetPage<SavedTrack>> getSavedTracks(@NotNull String authorization,
                                                         @Nullable Integer limit,
                                                         @Nullable Integer offset,
                                                         @Nullable String market) {
        requireNonNull(authorization);
        return libraryService.getSavedTracks(
                authorization,
                limit,
                offset,
                market
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    @RequiredScope({
            Scope.USER_LIBRARY_MODIFY
    })
    public Completable removeAlbums(@NotNull String authorization,
                                    @NotNull Collection<String> albumIds) {
        requireNonNull(authorization, albumIds);
        return libraryService.removeAlbums(
                authorization,
                commaJoin(albumIds)
        ).onErrorResumeNext(apiExceptionConverter::convertCompletable);
    }

    @RequiredScope({
            Scope.USER_LIBRARY_MODIFY
    })
    public Completable removeTracks(@NotNull String authorization,
                                    @NotNull Collection<String> trackIds) {
        requireNonNull(authorization, trackIds);
        return libraryService.removeTracks(
                authorization,
                commaJoin(trackIds)
        ).onErrorResumeNext(apiExceptionConverter::convertCompletable);
    }

    @RequiredScope({
            Scope.USER_LIBRARY_MODIFY
    })
    public Completable saveAlbums(@NotNull String authorization,
                                  @NotNull Collection<String> albumIds) {
        requireNonNull(authorization, albumIds);
        return libraryService.saveAlbums(
                authorization,
                commaJoin(albumIds)
        ).onErrorResumeNext(apiExceptionConverter::convertCompletable);
    }

    @RequiredScope({
            Scope.USER_LIBRARY_MODIFY
    })
    public Completable saveTracks(@NotNull String authorization,
                                  @NotNull Collection<String> trackIds) {
        requireNonNull(authorization, trackIds);
        return libraryService.saveTracks(
                authorization,
                commaJoin(trackIds)
        ).onErrorResumeNext(apiExceptionConverter::convertCompletable);
    }

    public static SpotifySDKBuilderSteps.ClientIdStep builder() {
        return new Builder();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Builder implements
            SpotifySDKBuilderSteps.ClientIdStep,
            SpotifySDKBuilderSteps.ClientSecretStep,
            SpotifySDKBuilderSteps.OptionalStep {

        private static final String API_BASE_URL = "https://api.spotify.com/";

        private String clientId;
        private String clientSecret;
        private boolean async;
        private Scheduler scheduler;
        private OkHttpClient okHttpClient;

        @Override
        public SpotifySDKBuilderSteps.ClientSecretStep clientId(String clientId) {
            Objects.requireNonNull(clientId);
            this.clientId = clientId;
            return this;
        }

        @Override
        public SpotifySDKBuilderSteps.OptionalStep clientSecret(String clientSecret) {
            Objects.requireNonNull(clientSecret);
            this.clientSecret = clientSecret;
            return this;
        }

        @Override
        public SpotifySDKBuilderSteps.OptionalStep async(boolean async) {
            this.async = async;
            return this;
        }


        @Override
        public SpotifySDKBuilderSteps.OptionalStep rxJavaScheduler(Scheduler scheduler) {
            Objects.requireNonNull(scheduler);
            this.scheduler = scheduler;
            return this;
        }

        @Override
        public SpotifySDKBuilderSteps.OptionalStep okHttpClient(OkHttpClient okHttpClient) {
            Objects.requireNonNull(okHttpClient);
            this.okHttpClient = okHttpClient;
            return this;
        }

        @Override
        public SpotifySDK build() {
            Retrofit retrofit = buildRetrofit();
            return new SpotifySDK(
                    clientId,
                    clientSecret,
                    retrofit.create(AuthorizationService.class),
                    retrofit.create(AlbumService.class),
                    retrofit.create(ArtistService.class),
                    retrofit.create(BrowseService.class),
                    retrofit.create(UserService.class),
                    retrofit.create(SearchService.class),
                    retrofit.create(FollowService.class),
                    retrofit.create(PersonalizationService.class),
                    retrofit.create(TrackService.class),
                    retrofit.create(PlayerService.class),
                    retrofit.create(PlaylistService.class),
                    retrofit.create(LibraryService.class),
                    createExceptionConverter(retrofit, ErrorHolder.class),
                    createExceptionConverter(retrofit, AuthError.class)
            );
        }

        private RxJavaExceptionConverter createExceptionConverter(Retrofit retrofit, Class<?> javaClass) {
            return new RxJavaExceptionConverter(
                    new AuthErrorConverter(
                            retrofit.responseBodyConverter(
                                    javaClass,
                                    new Annotation[0]
                            )
                    )
            );
        }

        private Retrofit buildRetrofit() {
            return new Retrofit.Builder()
                    .addCallAdapterFactory(scheduler != null
                            ? RxJava2CallAdapterFactory.createWithScheduler(scheduler)
                            : async
                            ? RxJava2CallAdapterFactory.createAsync()
                            : RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create(new SpotifyObjectMapper()))
                    .baseUrl(API_BASE_URL)
                    .client(okHttpClient != null ? okHttpClient : new OkHttpClient())
                    .build();
        }
    }

}
