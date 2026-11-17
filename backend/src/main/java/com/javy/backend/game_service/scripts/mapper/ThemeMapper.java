package com.javy.backend.game_service.scripts.mapper;

import com.javy.backend.game_service.model.Theme;
import com.javy.backend.game_service.scripts.dto.IGDBGenreThemeResponse;
import org.springframework.stereotype.Component;

@Component
public class ThemeMapper {

    /**
     * Converts an IGDB theme response into a theme entity.
     * Updates an existing entity when one is provided.
     */
    public Theme toEntity(IGDBGenreThemeResponse response, Theme existingTheme) {
        Theme theme = existingTheme != null
                ? existingTheme
                : new Theme();

        theme.setIgdbId(response.id());
        theme.setName(response.name());
        theme.setSlug(response.slug());

        return theme;
    }

}