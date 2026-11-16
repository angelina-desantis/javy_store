package com.javy.backend.game_service.scripts.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(IGDBProperties.class)
public class IGDBConfig {

    @Bean
    public RestClient igdbApiRestClient(IGDBProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.getApiBaseUrl())
                .build();
    }

    @Bean
    public RestClient igdbAuthRestClient() {
        return RestClient.builder().build();
    }
}