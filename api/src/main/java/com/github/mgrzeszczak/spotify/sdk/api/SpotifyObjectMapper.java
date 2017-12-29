package com.github.mgrzeszczak.spotify.sdk.api;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.mgrzeszczak.spotify.sdk.api.exception.SpotifySDKException;

class SpotifyObjectMapper extends ObjectMapper {

    SpotifyObjectMapper() {
        super();
        setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    <T> T deserialize(String json, Class<T> javaClass) {
        try {
            return readValue(json, javaClass);
        } catch (IOException e) {
            throw new SpotifySDKException("failed to deserialize from json", e);
        }
    }

    <T> String serialize(T object) {
        try {
            return writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SpotifySDKException("failed to serialize to json", e);
        }
    }

}
