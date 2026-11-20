package com.javy.backend.game_service.scripts.dto;

import java.util.List;
import java.util.UUID;

//The data fields I want returned when user searches for games
public record GameSearchResponse(
        UUID id,
        String name,
        String slug,
        String imageUrl,
        List<String> platform,
        List<String> genres,
        List<String> themes
        //float price --> implement in the future when we get an api for prices
) {
}
