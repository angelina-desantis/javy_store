package com.javy.backend.auth_service.service;

import com.javy.backend.shared.dto.ApiResult;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Handles authentication failures by returning a standardized HTTP 401 response.
 */
@Component
public class JWTAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JWTAuthEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Sends an unauthorized response when authentication is required but missing or invalid.
     *
     * @param request the incoming HTTP request
     * @param response the HTTP response
     * @param authException the authentication failure
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(
                response.getOutputStream(),
                ApiResult.failure("Authentication is required or the JWT is invalid.")
        );
    }
}