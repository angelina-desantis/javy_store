package com.javy.backend.game_service.scripts.importer;

import com.javy.backend.game_service.model.Platform;
import com.javy.backend.game_service.repository.PlatformRepository;
import com.javy.backend.game_service.scripts.client.IGDBClient;
import com.javy.backend.game_service.scripts.dto.IGDBPlatformResponse;
import com.javy.backend.game_service.scripts.mapper.PlatformMapper;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PlatformImporter {

    private static final String PLATFORM_QUERY = """
        fields
            name,
            platform_logo.url;

        where id = (5, 6, 12, 15, 37, 41, 48, 49, 130, 167, 169, 508);
        """;

    private final IGDBClient igdbClient;
    private final PlatformRepository platformRepository;
    private final PlatformMapper platformMapper;

    public PlatformImporter(
            IGDBClient igdbClient,
            PlatformRepository platformRepository,
            PlatformMapper platformMapper) {

        this.igdbClient = igdbClient;
        this.platformRepository = platformRepository;
        this.platformMapper = platformMapper;
    }

    /**
     * Fetches supported platforms from IGDB and saves them locally.
     *
     * @return number of platforms imported
     */
    @Transactional
    public int importPlatforms() {
        List<IGDBPlatformResponse> responses = igdbClient.postQuery(
                "platforms",
                PLATFORM_QUERY,
                new ParameterizedTypeReference<List<IGDBPlatformResponse>>() {}
        );

        List<Platform> platforms = responses.stream()
                .map(response -> {
                    Platform existing = platformRepository
                            .findByIgdbId(response.id())
                            .orElse(null);

                    return platformMapper.toEntity(response, existing);
                })
                .toList();

        platformRepository.saveAll(platforms);

        return platforms.size();
    }
}