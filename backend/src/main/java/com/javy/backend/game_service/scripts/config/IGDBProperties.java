package com.javy.backend.game_service.scripts.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "igdb")
public class IGDBProperties {

    private String clientId;
    private String clientSecret;
    private String apiBaseUrl = "https://api.igdb.com/v4";
    private String authUrl = "https://id.twitch.tv/oauth2/token";

}