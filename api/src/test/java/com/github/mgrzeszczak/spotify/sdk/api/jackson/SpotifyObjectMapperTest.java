package com.github.mgrzeszczak.spotify.sdk.api.jackson;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.mgrzeszczak.spotify.sdk.api.SpotifyObjectMapper;

public abstract class SpotifyObjectMapperTest {

    protected SpotifyObjectMapper mapper = new SpotifyObjectMapper();

    @SuppressWarnings("all")
    protected String loadResource(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemClassLoader().getResource(path).toURI())), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}