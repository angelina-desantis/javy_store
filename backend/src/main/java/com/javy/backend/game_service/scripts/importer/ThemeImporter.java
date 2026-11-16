package com.javy.backend.game_service.scripts.importer;

import com.javy.backend.game_service.model.Theme;
import com.javy.backend.game_service.repository.ThemeRepository;
import com.javy.backend.game_service.scripts.client.IGDBClient;
import com.javy.backend.game_service.scripts.dto.IGDBGenreThemeResponse;
import com.javy.backend.game_service.scripts.mapper.ThemeMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ThemeImporter {

    private static final String THEME_QUERY = """
            fields name, slug;
            limit 50;
        """;

    private final IGDBClient igdbClient;
    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;

    public ThemeImporter(IGDBClient igdbClient, ThemeRepository themeRepository, ThemeMapper themeMapper) {
        this.igdbClient = igdbClient;
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
    }

    /**
     * Fetches supported themes from IGDB and saves them locally.
     *
     * @return number of themes imported
     */
    @Transactional
    public int importThemes() {
        List<IGDBGenreThemeResponse> responses = igdbClient.postQuery(
                "themes",
                THEME_QUERY,
                new ParameterizedTypeReference<List<IGDBGenreThemeResponse>>() {}
        );

        List<Theme> themes = responses.stream()
                .map(response -> {
                    Theme existing = themeRepository
                            .findByIgdbId(response.id())
                            .orElse(null);

                    return themeMapper.toEntity(response, existing);
                })
                .toList();

        themeRepository.saveAll(themes);

        return themes.size();
    }
}
