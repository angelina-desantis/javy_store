package com.javy.backend.game_service.scripts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("expires_in")
        long expiresIn,

        @JsonProperty("token_type")
        String tokenType
) {}

