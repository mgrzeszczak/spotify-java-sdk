package com.github.mgrzeszczak.spotify.sdk.api;

import static com.github.mgrzeszczak.spotify.sdk.api.Utils.commaJoin;
import static com.github.mgrzeszczak.spotify.sdk.api.Utils.requireNonNull;
import static com.github.mgrzeszczak.spotify.sdk.api.Utils.toSnakeCaseMap;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.AlbumSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.Albums;
import com.github.mgrzeszczak.spotify.sdk.model.Artist;
import com.github.mgrzeszczak.spotify.sdk.model.Artists;
import com.github.mgrzeszczak.spotify.sdk.model.ArtistsCursorPage;
import com.github.mgrzeszczak.spotify.sdk.model.Category;
import com.github.mgrzeszczak.spotify.sdk.model.ErrorHolder;
import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;
import com.github.mgrzeszczak.spotify.sdk.model.PlaylistSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.Recommendations;
import com.github.mgrzeszczak.spotify.sdk.model.Track;
import com.github.mgrzeszczak.spotify.sdk.model.TrackAttributes;
import com.github.mgrzeszczak.spotify.sdk.model.Tracks;
import com.github.mgrzeszczak.spotify.sdk.model.UserPrivate;
import com.github.mgrzeszczak.spotify.sdk.model.UserPublic;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.AuthError;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@SuppressWarnings("unused")
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

    private final RxJavaExceptionConverter apiExceptionConverter;
    private final RxJavaExceptionConverter authExceptionConverter;

    private SpotifySDK(@NotNull String clientId,
                       @NotNull String clientSecret,
                       @NotNull AlbumService albumService,
                       @NotNull AuthorizationService authorizationService,
                       @NotNull ArtistService artistService,
                       @NotNull BrowseService browseService,
                       @NotNull UserService userService,
                       @NotNull SearchService searchService,
                       @NotNull FollowService followService,
                       @NotNull RxJavaExceptionConverter apiExceptionConverter,
                       @NotNull RxJavaExceptionConverter authExceptionConverter) {
        requireNonNull(
                clientId,
                clientSecret,
                albumService,
                authorizationService,
                artistService,
                browseService,
                userService,
                searchService,
                followService,
                apiExceptionConverter,
                authExceptionConverter
        );
        this.artistService = artistService;
        this.browseService = browseService;
        this.userService = userService;
        this.searchService = searchService;
        this.followService = followService;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.albumService = albumService;
        this.authorizationService = authorizationService;
        this.apiExceptionConverter = apiExceptionConverter;
        this.authExceptionConverter = authExceptionConverter;
    }

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

    public Flowable<Album> getAlbums(@NotNull String authorization,
                                     @NotNull Collection<String> albumIds,
                                     @Nullable String market) {
        requireNonNull(authorization, albumIds);
        return albumService.getAlbums(authorization, commaJoin(albumIds), market)
                .onErrorResumeNext(apiExceptionConverter::convertSingle)
                .flattenAsFlowable(Albums::getAlbums);
    }

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

    public Single<Artist> getArtist(@NotNull String authorization,
                                    @NotNull String artistId) {
        requireNonNull(authorization, artistId);
        return artistService.getArtist(
                authorization,
                artistId
        ).onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    public Flowable<Artist> getArtists(@NotNull String authorization,
                                       @NotNull Collection<String> artistIds) {
        requireNonNull(authorization, artistIds);
        return artistService.getArtists(
                authorization,
                commaJoin(artistIds)
        ).onErrorResumeNext(apiExceptionConverter::convertSingle)
                .flattenAsFlowable(Artists::getArtists);
    }

    public Flowable<Artist> getRelatedArtists(@NotNull String authorization,
                                              @NotNull String artistId) {
        requireNonNull(authorization, artistId);
        return artistService.getRelatedArtists(authorization, artistId)
                .onErrorResumeNext(apiExceptionConverter::convertSingle)
                .flattenAsFlowable(Artists::getArtists);
    }

    public Flowable<Track> getArtistTopTracks(@NotNull String authorization,
                                              @NotNull String artistId,
                                              @NotNull String country) {
        requireNonNull(authorization, artistId, country);
        return artistService.getArtistTopTracks(authorization, artistId, country)
                .onErrorResumeNext(apiExceptionConverter::convertSingle)
                .flattenAsFlowable(Tracks::getTracks);
    }

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

    public Single<UserPrivate> getCurrentUserProfile(@NotNull String authorization) {
        requireNonNull(authorization);
        return userService.getCurrentUserProfile(authorization)
                .onErrorResumeNext(apiExceptionConverter::convertSingle);
    }

    public Single<UserPublic> getUserProfile(@NotNull String authorization,
                                             @NotNull String userId) {
        requireNonNull(authorization, userId);
        return userService.getUserProfile(authorization, userId)
                .onErrorResumeNext(apiExceptionConverter::convertSingle);
    }


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

    // TODO: Personalization, Player, Playlists, Tracks, Library


    public static class Builder implements
            SpotifySDKBuilderSteps.ClientIdStep,
            SpotifySDKBuilderSteps.ClientSecretStep,
            SpotifySDKBuilderSteps.OptionalStep {

        private Builder() {

        }

        public static SpotifySDKBuilderSteps.ClientIdStep create() {
            return new Builder();
        }

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
                    retrofit.create(AlbumService.class),
                    retrofit.create(AuthorizationService.class),
                    retrofit.create(ArtistService.class),
                    retrofit.create(BrowseService.class),
                    retrofit.create(UserService.class),
                    retrofit.create(SearchService.class),
                    retrofit.create(FollowService.class),
                    new RxJavaExceptionConverter(new ApiErrorConverter(retrofit.responseBodyConverter(ErrorHolder.class, new Annotation[0]))),
                    new RxJavaExceptionConverter(new AuthErrorConverter(retrofit.responseBodyConverter(AuthError.class, new Annotation[0])))
            );
        }

        private Retrofit buildRetrofit() {
            return new Retrofit.Builder()
                    .addCallAdapterFactory(scheduler != null ? RxJava2CallAdapterFactory.createWithScheduler(scheduler) : async ? RxJava2CallAdapterFactory.createAsync() : RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create(new SpotifyObjectMapper()))
                    .baseUrl(API_BASE_URL)
                    .client(okHttpClient != null ? okHttpClient : new OkHttpClient())
                    .build();
        }
    }

}
