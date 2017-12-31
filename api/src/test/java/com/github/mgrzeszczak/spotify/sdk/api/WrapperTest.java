package com.github.mgrzeszczak.spotify.sdk.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mgrzeszczak.spotify.sdk.model.Artist;
import com.github.mgrzeszczak.spotify.sdk.model.OffsetPage;

public class WrapperTest extends SpotifyObjectMapperTest {

    @Test
    public void test() throws Exception {
        String artistsJson = loadResource("objects/search/artists.json");

        Wrapper<OffsetPage<Artist>> wrapper = mapper.readValue(artistsJson, new TypeReference<Wrapper<OffsetPage<Artist>>>() {
        });

        OffsetPage<Artist> data = wrapper.getData();
        assertThat(data.getItems()).hasSize(1);
    }


}