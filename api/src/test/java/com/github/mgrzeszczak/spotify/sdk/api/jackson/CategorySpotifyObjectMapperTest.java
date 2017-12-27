package com.github.mgrzeszczak.spotify.sdk.api.jackson;

import org.junit.Test;

import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.Category;

public class CategorySpotifyObjectMapperTest extends SpotifyObjectMapperTest {

    @Test
    public void shouldDeserializeAndSerializeCategory() {
        String json = loadResource("objects/category/category.json");
        Category category = mapper.deserialize(json, Category.class);
        String serialized = mapper.serialize(category);
    }


}
