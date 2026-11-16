package com.javy.backend.game_service.scripts.importer;

import com.javy.backend.game_service.model.Genre;
import com.javy.backend.game_service.repository.GenreRepository;
import com.javy.backend.game_service.scripts.client.IGDBClient;
import com.javy.backend.game_service.scripts.dto.IGDBGenreThemeResponse;
import com.javy.backend.game_service.scripts.mapper.GenreMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GenreImporter {

    private static final String GENRE_QUERY = """
            fields
                name,
                slug;
            limit 50;
        """;

    private final IGDBClient igdbClient;
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreImporter(
            IGDBClient igdbClient,
            GenreRepository genreRepository,
            GenreMapper genreMapper) {

        this.igdbClient = igdbClient;
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    /**
     * Fetches supported genres from IGDB and saves them locally.
     *
     * @return number of genres imported
     */
    @Transactional
    public int importGenres() {
        List<IGDBGenreThemeResponse> responses = igdbClient.postQuery(
                "genres",
                GENRE_QUERY,
                new ParameterizedTypeReference<List<IGDBGenreThemeResponse>>() {}
        );

        List<Genre> genres = responses.stream()
                .map(response -> {
                    Genre existing = genreRepository
                            .findByIgdbId(response.id())
                            .orElse(null);

                    return genreMapper.toEntity(response, existing);
                })
                .toList();

        genreRepository.saveAll(genres);

        return genres.size();
    }
}
