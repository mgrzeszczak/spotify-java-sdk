package com.github.mgrzeszczak.spotify.sdk.api;

import org.junit.Test;

import com.github.mgrzeszczak.spotify.sdk.model.Track;

public class TrackSpotifyObjectMapperTest extends SpotifyObjectMapperTest {

    @Test
    public void shouldDeserializeAndSerializeTrack() {
        String trackJson = loadResource("objects/track/track.json");
        Track track = mapper.deserialize(trackJson, Track.class);
        String serialized = mapper.serialize(track);
    }


}
