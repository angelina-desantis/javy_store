package com.javy.backend.game_service.scripts.runner;

import com.javy.backend.game_service.scripts.importer.PlatformImporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "igdb.import.platform.enabled",
        havingValue = "true"
)
public class PlatformRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(PlatformRunner.class);

    private final PlatformImporter platformImporter;

    public PlatformRunner(PlatformImporter platformImporter) {
        this.platformImporter = platformImporter;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Starting IGDB platform import...");

        int importedCount = platformImporter.importPlatforms();

        logger.info(
                "Successfully imported {} platforms from IGDB.",
                importedCount
        );
    }
}