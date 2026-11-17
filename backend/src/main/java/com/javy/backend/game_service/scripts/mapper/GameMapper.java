
package com.javy.backend.game_service.scripts.mapper;

import com.javy.backend.game_service.model.Game;
import com.javy.backend.game_service.model.Genre;
import com.javy.backend.game_service.model.Platform;
import com.javy.backend.game_service.model.Theme;
import com.javy.backend.game_service.scripts.dto.IGDBGameResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GameMapper {

    /**
     * Converts an IGDB game response into a game entity.
     * Updates an existing entity when one is provided.
     * Associates only genres, platforms, and themes that exist locally.
     *
     * @param response IGDB game response
     * @param popularity game's popularity score
     * @param existingGame existing game entity, if available
     * @param genres available genres indexed by IGDB ID
     * @param platforms available platforms indexed by IGDB ID
     * @param themes available themes indexed by IGDB ID
     * @return mapped game entity
     */
    public Game toEntity(
            IGDBGameResponse response,
            Double popularity,
            Game existingGame,
            Map<Integer, Genre> genres,
            Map<Integer, Platform> platforms,
            Map<Integer, Theme> themes
    ) {

        Game game = existingGame != null ? existingGame : new Game();

        game.setIgdbId(response.id());
        game.setName(response.name());
        game.setSlug(response.slug());
        game.setSummary(response.summary());
        game.setImageUrl("https:" + response.cover().url());
        game.setFirstReleaseDate(
                Instant.ofEpochSecond(response.firstReleaseDate())
        );
        game.setPopularityScore(popularity);

        game.setGenres(mapEntities(response.genres(), genres));
        game.setPlatforms(mapEntities(response.platforms(), platforms));
        game.setThemes(mapEntities(response.themes(), themes));

        return game;
    }

    /**
     * Resolves IGDB IDs to locally available entities.
     * IDs without matching entities are ignored.
     *
     * @param ids IGDB IDs
     * @param availableEntities locally available entities indexed by IGDB ID
     * @param <T> entity type
     * @return matching entities
     */
    private <T> Set<T> mapEntities(
            List<Integer> ids,
            Map<Integer, T> availableEntities
    ) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }

        return ids.stream()
                .map(availableEntities::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}