package com.javy.backend.game_service.scripts.dto;

import java.util.UUID;

//The data fields I want returned when user searches for games
public record GameSearchResponse(
        UUID id,
        String name,
        String slug,
        String imageUrl,
        String platform
        //float price --> implement in the future when we get an api for prices
) {
}
