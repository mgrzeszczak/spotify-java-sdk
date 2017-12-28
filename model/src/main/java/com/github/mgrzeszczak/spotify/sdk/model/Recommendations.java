package com.github.mgrzeszczak.spotify.sdk.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Recommendations {

    private List<RecommendationsSeed> seeds;

    private List<TrackSimplified> tracks;

}
