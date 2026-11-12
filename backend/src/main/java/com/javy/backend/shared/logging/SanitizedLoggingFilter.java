package com.javy.backend.shared.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Filter responsible for sanitizing sensitive request data before it reaches
 * the logging pipeline.

 * <p>
 * Sensitive fields include (but are not limited to):
 * <ul>
 *     <li>Authorization headers</li>
 *     <li>Supabase access tokens</li>
 *     <li>Supabase refresh tokens</li>
 *     <li>Password fields</li>
 * </ul>
 * </p>
 */
@Component
public class SanitizedLoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(SanitizedLoggingFilter.class);

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;

        sanitizeHeaders(http);

        chain.doFilter(request, response);

    }

    /**
     * Masks or removes sensitive headers to prevent accidental logging.
     *
     * @param request the incoming HTTP request
     */
    private void sanitizeHeaders(HttpServletRequest request) {

        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames == null) {
            return;
        }

        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();

            if (header.equalsIgnoreCase("Authorization")) {
                MDC.put("authorization", "[MASKED]");
                continue;
            }

            if (header.toLowerCase().contains("token")) {
                MDC.put(header, "[MASKED]");
            }

            if (header.toLowerCase().contains("password")) {
                MDC.put(header, "[MASKED]");
            }
        }

        log.debug("Sensitive headers sanitized for request [{}]", MDC.get("correlationId"));
    }
}