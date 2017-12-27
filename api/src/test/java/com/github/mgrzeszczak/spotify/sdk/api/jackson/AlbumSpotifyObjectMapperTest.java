package com.github.mgrzeszczak.spotify.sdk.api.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.github.mgrzeszczak.spotify.sdk.model.Album;

public class AlbumSpotifyObjectMapperTest extends SpotifyObjectMapperTest {

    @Test
    public void shouldDeserializeAndSerializeAlbum() {
        String albumJson = loadResource("objects/album/album.json");
        Album album = mapper.deserialize(albumJson, Album.class);
        String serialized = mapper.serialize(album);
    }


}
