package com.javy.backend.shared.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configures application security.
 *
 * <p>
 * This configuration enables JWT-based authentication using Supabase as
 * the OAuth2 resource server, configures Cross-Origin Resource Sharing
 * (CORS), disables CSRF protection for the REST API, and defines which
 * endpoints require authentication.
 * </p>
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /**
     * Configures the application's HTTP security.
     *
     * <p>
     * Public endpoints such as Swagger documentation are accessible
     * without authentication, while all remaining endpoints require
     * a valid JWT.
     * </p>
     *
     * @param http Spring Security HTTP configuration
     * @return configured security filter chain
     * @throws Exception if security configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/public/**",
                                "/auth/signup",
                                "/auth/login",
                                "/auth/refresh",
                                "/auth/reset-password",
                                "/auth/resend-verification",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }

    /**
     * Configures Cross-Origin Resource Sharing (CORS) for the application.
     *
     * <p>
     * Requests originating from approved frontend applications are
     * permitted to access the API using the configured HTTP methods
     * and request headers.
     * </p>
     *
     * @return CORS configuration source used by Spring Security
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:3000"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}