package com.javy.backend.shared.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI / Swagger documentation.
 *
 * <p>
 * Defines global API documentation metadata and configures JWT bearer
 * authentication support within Swagger UI.
 * </p>
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "JAVY Store API",
                version = "1.0",
                description = """
                        Backend API for the E-Commerce application.
                        
                        Authentication is handled through JWT tokens.
                        """
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication using JWT tokens",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}