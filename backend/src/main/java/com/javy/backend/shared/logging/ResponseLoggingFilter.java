package com.javy.backend.shared.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filter responsible for logging response metadata such as HTTP status code
 * and total execution time for each request.
 *
 * <p>
 * This filter works together with {@link RequestLoggingFilter} and
 * {@link CorrelationIdFilter} to provide full request lifecycle visibility.
 * </p>
 *
 * <p>
 * Execution time is measured from the moment the request enters the filter
 * until the response is fully returned to the client.
 * </p>
 */
@Component
public class ResponseLoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(ResponseLoggingFilter.class);

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        long start = System.currentTimeMillis();

        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;

        String correlationId = MDC.get("correlationId");
        String method = httpReq.getMethod();
        String path = httpReq.getRequestURI();
        int status = httpRes.getStatus();

        log.info(
                "Response [{}]: method={}, path={}, status={}, duration={}ms",
                correlationId,
                method,
                path,
                status,
                duration
        );
    }
}