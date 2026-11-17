package com.javy.backend.game_service.scripts.client;

import com.javy.backend.game_service.scripts.config.IGDBProperties;
import com.javy.backend.game_service.scripts.dto.TokenResponse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;

@Component
public class IGDBClient {

    private final RestClient apiClient;
    private final RestClient authClient;
    private final IGDBProperties properties;

    private volatile String accessToken;
    private volatile Instant tokenExpiration;

    public IGDBClient(
            RestClient igdbApiRestClient,
            RestClient igdbAuthRestClient,
            IGDBProperties properties)
    {
        this.apiClient = igdbApiRestClient;
        this.authClient = igdbAuthRestClient;
        this.properties = properties;
    }

    /**
     * Sends an APICalypse query to the specified IGDB endpoint.
     *
     * @param endpoint IGDB endpoint, such as "platforms" or "games"
     * @param query APICalypse query to execute
     * @param responseType expected response type
     * @return deserialized IGDB response
     */
    public <T> T postQuery(
            String endpoint,
            String query,
            ParameterizedTypeReference<T> responseType) {

        return apiClient.post()
                .uri("/{endpoint}", endpoint)
                .header("Client-ID", properties.getClientId())
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + getAccessToken()
                )
                .contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.APPLICATION_JSON)
                .body(query)
                .retrieve()
                .body(responseType);
    }

    /**
     * Obtains a valid Twitch OAuth access token.
     * Reuses the cached token until shortly before expiration.
     *
     * @return valid OAuth access token
     */
    private String getAccessToken() {

        if (accessToken != null
                && tokenExpiration != null
                && Instant.now().isBefore(tokenExpiration)) {
            return accessToken;
        }

        synchronized (this) {

            if (accessToken != null
                    && tokenExpiration != null
                    && Instant.now().isBefore(tokenExpiration)) {
                return accessToken;
            }

            var uri = UriComponentsBuilder
                    .fromUriString(properties.getAuthUrl())
                    .queryParam("client_id", properties.getClientId())
                    .queryParam("client_secret", properties.getClientSecret())
                    .queryParam("grant_type", "client_credentials")
                    .build()
                    .encode()
                    .toUri();

            TokenResponse response = authClient.post()
                    .uri(uri)
                    .retrieve()
                    .body(TokenResponse.class);

            if (response == null || response.accessToken() == null) {
                throw new IllegalStateException(
                        "Failed to obtain IGDB access token."
                );
            }

            this.accessToken = response.accessToken();

            // Refresh the token 60 seconds before actual expiration.
            this.tokenExpiration = Instant.now().plusSeconds(
                    Math.max(1, response.expiresIn() - 60)
            );

            return accessToken;
        }
    }
}