package com.javy.backend.game_service.scripts.mapper;

import com.javy.backend.game_service.model.Platform;
import com.javy.backend.game_service.scripts.dto.IGDBPlatformResponse;
import org.springframework.stereotype.Component;

@Component
public class PlatformMapper {

    /**
     * Converts an IGDB platform response into a Platform entity.
     * Updates an existing entity when one is provided.
     */
    public Platform toEntity(IGDBPlatformResponse response, Platform existingPlatform) {
        Platform platform = existingPlatform != null
                ? existingPlatform
                : new Platform();

        platform.setIgdbId(response.id());
        platform.setName(response.name());
        platform.setImageUrl("https:" + response.platformLogo().url());

        return platform;
    }
}