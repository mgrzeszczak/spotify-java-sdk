package com.github.mgrzeszczak.spotify.sdk.model.authorization;

public enum Scope {

    USER_READ_PRIVATE,
    USER_READ_EMAIL,
    USER_READ_BIRTHDATE,
    PLAYLIST_READ_PRIVATE,
    PLAYLIST_MODIFY_PRIVATE,
    PLAYLIST_MODIFY_PUBLIC,
    PLAYLIST_READ_COLLABORATIVE,
    USER_TOP_READ,
    USER_READ_RECENTLY_PLAYED,
    USER_LIBRARY_READ,
    USER_LIBRARY_MODIFY,
    USER_READ_CURRENTLY_PLAYING,
    USER_MODIFY_PLAYBACK_STATE,
    USER_READ_PLAYBACK_STATE,
    USER_FOLLOW_MODIFY,
    USER_FOLLOW_READ,
    STREAMING,
    UGC_IMAGE_UPLOAD;

    public String getValue() {
        return name().toLowerCase().replaceAll("_", "-");
    }

}
