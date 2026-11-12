package com.javy.backend.shared.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filter responsible for detecting slow requests and logging a warning
 * when execution time exceeds a configurable threshold.
 */
@Component
public class SlowRequestLoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(SlowRequestLoggingFilter.class);

    private static final long SLOW_REQUEST_THRESHOLD_MS = 500;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;

        long start = System.currentTimeMillis();

        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;

        if (duration > SLOW_REQUEST_THRESHOLD_MS) {

            String correlationId = MDC.get("correlationId");
            String userId = MDC.get("userId");
            String method = http.getMethod();
            String path = http.getRequestURI();

            log.warn(
                    "Slow request [{}]: user={}, method={}, path={}, duration={}ms",
                    correlationId,
                    (userId != null ? userId : "anonymous"),
                    method,
                    path,
                    duration
            );
        }
    }
}