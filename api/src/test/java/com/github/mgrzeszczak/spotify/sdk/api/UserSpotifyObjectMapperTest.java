package com.github.mgrzeszczak.spotify.sdk.api;

import org.junit.Test;

import com.github.mgrzeszczak.spotify.sdk.model.UserPrivate;
import com.github.mgrzeszczak.spotify.sdk.model.UserPublic;

public class UserSpotifyObjectMapperTest extends SpotifyObjectMapperTest {

    @Test
    public void shouldDeserializeAndSerializeUserPublic() {
        String json = loadResource("objects/user/user_public.json");
        UserPublic user = mapper.deserialize(json, UserPublic.class);
        String serialized = mapper.serialize(user);
    }

    @Test
    public void shouldDeserializeAndSerializeUserPrivate() {
        String json = loadResource("objects/user/user_private.json");
        UserPrivate user = mapper.deserialize(json, UserPrivate.class);
        String serialized = mapper.serialize(user);
    }


}
