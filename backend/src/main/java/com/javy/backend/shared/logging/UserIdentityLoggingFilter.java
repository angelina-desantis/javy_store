package com.javy.backend.shared.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * Filter responsible for extracting the authenticated user's identity
 * from the Authorization header and storing it in the Mapped Diagnostic
 * Context (MDC) for logging purposes.
 *
 * <p>
 * Decodes the payload to extract the {@code sub} claim,
 * which Supabase uses as the user UUID.
 * </p>
 */
@Component
public class UserIdentityLoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(UserIdentityLoggingFilter.class);

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;

        String authHeader = http.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String[] parts = token.split("\\.");
                if (parts.length == 3) {
                    String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);

                    Map<String, Object> claims = new com.fasterxml.jackson.databind.ObjectMapper()
                            .readValue(payloadJson, Map.class);

                    Object sub = claims.get("sub");

                    if (sub != null) {
                        MDC.put("userId", sub.toString());
                        log.info("Authenticated user [{}] added to MDC", sub);
                    }
                }
            } catch (Exception ex) {
                log.warn("Unable to extract user identity from JWT: {}", ex.getMessage());
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("userId");
        }
    }
}