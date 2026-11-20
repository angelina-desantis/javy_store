package com.javy.backend.game_service.scripts.dto;

import java.util.List;
import java.util.UUID;

/*
Columns of the game that is going to be returned to
the front end by the search and filter endpoints
 */
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
