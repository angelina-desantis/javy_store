package com.javy.backend.game_service.scripts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record IGDBGameResponse(
        Integer id,
        String name,
        @JsonProperty("first_release_date")
        Long firstReleaseDate,
        String slug,
        String summary,
        IGDBImageResponse cover,
        List<Integer> platforms,
        List<Integer> genres,
        List<Integer> themes

) {
}
