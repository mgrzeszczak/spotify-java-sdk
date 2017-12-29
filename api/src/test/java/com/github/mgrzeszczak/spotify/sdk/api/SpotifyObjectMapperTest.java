package com.github.mgrzeszczak.spotify.sdk.api;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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