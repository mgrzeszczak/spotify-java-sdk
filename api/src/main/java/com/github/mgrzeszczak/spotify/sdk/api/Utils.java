package com.github.mgrzeszczak.spotify.sdk.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Utils {

    static final String CONTENT_TYPE_APPLICATION_JSON = "Content-Type: application/json";
    static final String CONTENT_TYPE_IMAGE_JPEG = "Content-Type: image/jpeg";

    private static final SpotifyObjectMapper MAPPER = new SpotifyObjectMapper();

    private Utils() {
        throw new AssertionError("no instances");
    }

    @SuppressWarnings("unchecked")
    static <T> Map<String, Object> toSnakeCaseMap(T object, String keyPrefix) {
        if (object == null) {
            return new HashMap<>();
        }
        Map<String, Object> map = MAPPER.deserialize(MAPPER.serialize(object), Map.class);
        Map<String, Object> output = new HashMap<>();
        map.entrySet().stream().filter(e -> e.getValue() != null).forEach(e -> output.put(keyPrefix + e.getKey(), e.getValue()));
        return output;
    }

    static <T> String commaJoin(Collection<T> objects, Function<T, String> toString) {
        if (objects == null) {
            return null;
        }
        return objects.stream()
                .map(toString)
                .collect(Collectors.joining(","));
    }

    static String commaJoin(Collection<String> values) {
        return commaJoin(values, Function.identity());
    }

    static void requireNonNull(Object... objects) {
        Stream.of(objects).forEach(Objects::requireNonNull);
    }

}
