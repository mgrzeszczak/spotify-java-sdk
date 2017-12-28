package com.github.mgrzeszczak.spotify.sdk.api.jackson;

import org.junit.Test;

import com.github.mgrzeszczak.spotify.sdk.model.Playlist;

public class PlaylistSpotifyObjectMapperTest extends SpotifyObjectMapperTest {

    @Test
    public void shouldDeserializeAndSerializePlaylist() {
        String playlistJson = loadResource("objects/playlist/playlist.json");
        Playlist playlist = mapper.deserialize(playlistJson, Playlist.class);
        String serialized = mapper.serialize(playlist);
    }

}
