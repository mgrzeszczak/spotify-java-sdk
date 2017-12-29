package com.github.mgrzeszczak.spotify.sdk.api;

import org.junit.Test;

import com.github.mgrzeszczak.spotify.sdk.model.Recommendations;

public class RecommendationsSpotifyObjectMapperTest extends SpotifyObjectMapperTest {

    @Test
    public void shouldDeserializeAndSerializeRecommendations() {
        String json = loadResource("objects/recommendations/recommendations.json");
        Recommendations recommendations = mapper.deserialize(json, Recommendations.class);
        String serialized = mapper.serialize(recommendations);
    }


}
