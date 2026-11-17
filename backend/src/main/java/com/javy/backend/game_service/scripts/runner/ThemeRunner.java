package com.javy.backend.game_service.scripts.runner;

import com.javy.backend.game_service.scripts.importer.ThemeImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "igdb.import.theme.enabled",
        havingValue = "true"
)
public class ThemeRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ThemeRunner.class);

    private final ThemeImporter themeImporter;

    public ThemeRunner(ThemeImporter themeImporter) {
        this.themeImporter = themeImporter;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Starting theme runner");

        int importedCount = themeImporter.importThemes();

        logger.info(
                "Successfully imported {} themes from IGDB.",
                importedCount
        );
    }
}