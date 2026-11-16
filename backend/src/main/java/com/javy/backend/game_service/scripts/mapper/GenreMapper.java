package com.javy.backend.game_service.scripts.mapper;

import com.javy.backend.game_service.model.Genre;
import com.javy.backend.game_service.scripts.dto.IGDBGenreThemeResponse;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    /**
     * Converts an IGDB genre response into a genre entity.
     * Updates an existing entity when one is provided.
     */
    public Genre toEntity(IGDBGenreThemeResponse response, Genre existingGenre) {
        Genre genre = existingGenre != null
                ? existingGenre
                : new Genre();

        genre.setIgdbId(response.id());
        genre.setName(response.name());
        genre.setSlug(response.slug());

        return genre;
    }

}
