package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferPlaybackParameters {

    private boolean play;

    private List<String> deviceIds;

}
