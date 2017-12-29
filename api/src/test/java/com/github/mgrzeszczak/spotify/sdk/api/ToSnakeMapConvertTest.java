package com.github.mgrzeszczak.spotify.sdk.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.mgrzeszczak.spotify.sdk.model.TrackAttributes;

public class ToSnakeMapConvertTest extends SpotifyObjectMapperTest {

    @Test
    public void shouldConvertToSnakeCaseMap() {
        TrackAttributes attributes = TrackAttributes.builder()
                .acousticness(1.0f)
                .danceability(1.0f)
                .durationMs(100)
                .build();

        Map<String, Object> map = toSnakeCaseMap(attributes, "min_");

        assertThat(map).isEqualTo(
                new HashMap<String, Object>() {{
                    put("min_acousticness", 1.0);
                    put("min_danceability", 1.0);
                    put("min_duration_ms", 100);
                }}
        );
    }


    private <T> Map<String, Object> toSnakeCaseMap(T object, String keyPrefix) {
        if (object == null) {
            return new HashMap<>();
        }
        Map<String, Object> map = mapper.deserialize(mapper.serialize(object), Map.class);
        Map<String, Object> output = new HashMap<>();
        map.entrySet().stream().filter(e -> e.getValue() != null).forEach(e -> output.put(keyPrefix + e.getKey(), e.getValue()));
        return output;
    }

}
