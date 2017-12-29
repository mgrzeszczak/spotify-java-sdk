package com.github.mgrzeszczak.spotify.sdk.api;

import org.junit.Test;

import com.github.mgrzeszczak.spotify.sdk.model.Artist;

public class ArtistSpotifyObjectMapperTest extends SpotifyObjectMapperTest {

    @Test
    public void shouldDeserializeAndSerializeArtist() {
        String artistJson = loadResource("objects/artist/artist.json");
        Artist artist = mapper.deserialize(artistJson, Artist.class);
        String serialized = mapper.serialize(artist);
    }

}
