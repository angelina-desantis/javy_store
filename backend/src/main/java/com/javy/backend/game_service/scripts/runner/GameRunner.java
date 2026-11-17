package com.javy.backend.game_service.scripts.runner;

import com.javy.backend.game_service.scripts.importer.GameImporter;
import com.javy.backend.game_service.scripts.importer.PopularityImporter;
import com.javy.backend.game_service.scripts.dto.IGDBPopularityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(
        name = "igdb.import.game.enabled",
        havingValue = "true"
)
public class GameRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(GameRunner.class);

    private final PopularityImporter popularityImporter;
    private final GameImporter gameImporter;

    public GameRunner(
            PopularityImporter popularityImporter,
            GameImporter gameImporter
    ) {
        this.popularityImporter = popularityImporter;
        this.gameImporter = gameImporter;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Starting game import...");

        List<IGDBPopularityResponse> popularityResponses = popularityImporter.importPopularGames();

        logger.info(
                "Retrieved {} popularity records from IGDB.",
                popularityResponses.size()
        );

        int importedCount = gameImporter.importGames(popularityResponses);

        logger.info(
                "Successfully imported {} games from IGDB.",
                importedCount
        );
    }
}