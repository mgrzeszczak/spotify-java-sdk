package com.github.mgrzeszczak.spotify.sdk.api;

import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;

public interface SpotifySDKBuilderSteps {

    interface ClientIdStep {

        ClientSecretStep clientId(String clientId);

    }

    interface ClientSecretStep {

        OptionalStep clientSecret(String clientSecret);

    }

    interface OptionalStep {

        SpotifySDK build();

        OptionalStep async(boolean async);

        OptionalStep rxJavaScheduler(Scheduler scheduler);

        OptionalStep okHttpClient(OkHttpClient okHttpClient);

    }

}
