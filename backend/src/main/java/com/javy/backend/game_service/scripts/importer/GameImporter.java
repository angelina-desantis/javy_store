package com.javy.backend.game_service.scripts.importer;

import com.javy.backend.game_service.model.Game;
import com.javy.backend.game_service.model.Genre;
import com.javy.backend.game_service.model.Platform;
import com.javy.backend.game_service.model.Theme;
import com.javy.backend.game_service.repository.GameRepository;
import com.javy.backend.game_service.repository.GenreRepository;
import com.javy.backend.game_service.repository.PlatformRepository;
import com.javy.backend.game_service.repository.ThemeRepository;
import com.javy.backend.game_service.scripts.client.IGDBClient;
import com.javy.backend.game_service.scripts.dto.IGDBGameResponse;
import com.javy.backend.game_service.scripts.dto.IGDBPopularityResponse;
import com.javy.backend.game_service.scripts.mapper.GameMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GameImporter {

    private static final int BATCH_SIZE = 100;

    private final IGDBClient igdbClient;
    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;
    private final ThemeRepository themeRepository;
    private final GameMapper gameMapper;

    public GameImporter(
            IGDBClient igdbClient,
            GameRepository gameRepository,
            GenreRepository genreRepository,
            PlatformRepository platformRepository,
            ThemeRepository themeRepository,
            GameMapper gameMapper
    ) {
        this.igdbClient = igdbClient;
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
        this.themeRepository = themeRepository;
        this.gameMapper = gameMapper;
    }

    /**
     * Fetches supported games from IGDB and saves them locally.
     *
     * @param popularityResponses popularity records used to identify games
     * @return number of games imported
     */
    public int importGames(List<IGDBPopularityResponse> popularityResponses) {

        if (popularityResponses == null || popularityResponses.isEmpty()) {
            return 0;
        }

        int importedCount = 0;

        for (int i = 0; i < popularityResponses.size(); i += BATCH_SIZE) {

            List<IGDBPopularityResponse> batch = popularityResponses.subList(
                    i,
                    Math.min(i + BATCH_SIZE, popularityResponses.size())
            );

            importedCount += importBatch(batch);
        }

        return importedCount;
    }

    /**
     * Imports a batch of games and updates existing records when necessary.
     *
     * @param popularityBatch popularity records for this batch
     * @return number of games saved
     */
    private int importBatch(List<IGDBPopularityResponse> popularityBatch) {

        String query = buildQuery(popularityBatch);

        List<IGDBGameResponse> gameResponses = igdbClient.postQuery(
                "games",
                query,
                new ParameterizedTypeReference<List<IGDBGameResponse>>() {}
        );

        if (gameResponses == null || gameResponses.isEmpty()) {
            return 0;
        }

        // Build a map of game IDs to popularity scores.
        Map<Integer, Double> popularityMap = popularityBatch.stream()
                .collect(Collectors.toMap(
                        IGDBPopularityResponse::gameId,
                        IGDBPopularityResponse::value,
                        (first, second) -> first
                ));

        // Collect all IGDB game IDs in this batch.
        List<Integer> gameIds = gameResponses.stream()
                .map(IGDBGameResponse::id)
                .toList();

        // Retrieve existing games in one database query.
        Map<Integer, Game> existingGames = gameRepository
                .findAllByIgdbIdIn(gameIds)
                .stream()
                .collect(Collectors.toMap(
                        Game::getIgdbId,
                        Function.identity()
                ));

        // Collect unique genre, platform, and theme IDs.
        List<Integer> genreIds = collectIds(gameResponses, IGDBGameResponse::genres);
        List<Integer> platformIds = collectIds(gameResponses, IGDBGameResponse::platforms);
        List<Integer> themeIds = collectIds(gameResponses, IGDBGameResponse::themes);

        // Retrieve existing reference entities in three bulk queries.
        Map<Integer, Genre> genres = genreRepository
                .findAllByIgdbIdIn(genreIds)
                .stream()
                .collect(Collectors.toMap(Genre::getIgdbId, Function.identity()));

        Map<Integer, Platform> platforms = platformRepository
                .findAllByIgdbIdIn(platformIds)
                .stream()
                .collect(Collectors.toMap(Platform::getIgdbId, Function.identity()));

        Map<Integer, Theme> themes = themeRepository
                .findAllByIgdbIdIn(themeIds)
                .stream()
                .collect(Collectors.toMap(Theme::getIgdbId, Function.identity()));

        // Map game responses and their associated entities.
        List<Game> games = gameResponses.stream()
                .map(response -> gameMapper.toEntity(
                        response,
                        popularityMap.get(response.id()),
                        existingGames.get(response.id()),
                        genres,
                        platforms,
                        themes
                ))
                .toList();

        // Save games and populate their join tables.
        gameRepository.saveAll(games);

        return games.size();
    }

    /**
     * Collects unique IGDB IDs from a list of game responses.
     *
     * @param responses game responses
     * @param idExtractor extracts the requested IDs from each response
     * @return unique IDs
     */
    private List<Integer> collectIds(
            List<IGDBGameResponse> responses,
            Function<IGDBGameResponse, List<Integer>> idExtractor
    ) {
        return responses.stream()
                .map(idExtractor)
                .filter(ids -> ids != null)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }

    /**
     * Constructs an IGDB games query using the supplied game IDs.
     *
     * @param popularityResponses popularity records containing IGDB game IDs
     * @return dynamically constructed IGDB query
     */
    private String buildQuery(List<IGDBPopularityResponse> popularityResponses) {
        String gameIds = popularityResponses.stream()
                .map(response -> response.gameId().toString())
                .collect(Collectors.joining(", "));

        return """
                fields
                    name,
                    platforms,
                    genres,
                    themes,
                    slug,
                    summary,
                    cover.url,
                    first_release_date;

                where id = (%s);
                limit 500;
                """.formatted(gameIds);
    }
}