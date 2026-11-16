package com.javy.backend.game_service.scripts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IGDBPlatformResponse(
        Integer id,
        String name,

        @JsonProperty("platform_logo")
        IGDBImageResponse platformLogo
) {}
