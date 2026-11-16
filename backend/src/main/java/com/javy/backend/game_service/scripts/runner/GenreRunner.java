package com.javy.backend.game_service.scripts.runner;

import com.javy.backend.game_service.scripts.importer.GenreImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "igdb.import.genre.enabled",
        havingValue = "true"
)
public class GenreRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(GenreRunner.class);

    private final GenreImporter genreImporter;

    public GenreRunner(GenreImporter genreImporter) {
        this.genreImporter = genreImporter;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Starting IGDB genre import...");

        int importedCount = genreImporter.importGenres();

        logger.info(
                "Successfully imported {} genres from IGDB.",
                importedCount
        );
    }
}
