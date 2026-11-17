package com.javy.backend.game_service.scripts.importer;

import com.javy.backend.game_service.scripts.client.IGDBClient;
import com.javy.backend.game_service.scripts.dto.IGDBPopularityResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PopularityImporter {

    private static final String POP_QUERY = """
                fields game_id, value;
                where popularity_type = 4;
                sort value desc;
                limit 500;
            """;

    private final IGDBClient igdbClient;

    public PopularityImporter(IGDBClient igdbClient) {
        this.igdbClient = igdbClient;
    }

    /**
     * Fetches popular games from IGDB and converts them to a list.
     *
     * @return list of popular games with their scores
     */
    public List<IGDBPopularityResponse> importPopularGames() {
        return igdbClient.postQuery(
                "popularity_primitives",
                POP_QUERY,
                new ParameterizedTypeReference<List<IGDBPopularityResponse>>() {}
        );
    }

}