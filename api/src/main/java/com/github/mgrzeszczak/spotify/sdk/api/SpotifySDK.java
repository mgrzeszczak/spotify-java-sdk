package com.github.mgrzeszczak.spotify.sdk.api;

import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public interface SpotifySDK extends AlbumService, ArtistService, AuthenticationService {

    class Builder {

        private static final String API_BASE_URL = "https://api.spotify.com/";

        private boolean async;
        private Scheduler scheduler;

        public Builder async(boolean async) {
            this.async = async;
            return this;
        }

        public Builder scheduler(Scheduler scheduler) {
            this.scheduler = scheduler;
            return this;
        }

        private Retrofit buildRetrofit() {
            return new Retrofit.Builder()
                    .addCallAdapterFactory(scheduler != null ? RxJava2CallAdapterFactory.createWithScheduler(scheduler) : async ? RxJava2CallAdapterFactory.createAsync() : RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create(new SpotifyObjectMapper()))
                    .baseUrl(API_BASE_URL)
                    .client(new OkHttpClient())
                    .build();
        }

        public SpotifySDK build() {
            Retrofit retrofit = buildRetrofit();
            return new SpotifySDKImpl(retrofit);
        }
    }

}
