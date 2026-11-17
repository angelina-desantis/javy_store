package com.javy.backend.game_service.scripts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IGDBPopularityResponse(
        Integer id,

        @JsonProperty("game_id")
        Integer gameId,

        Double value
) {
}
