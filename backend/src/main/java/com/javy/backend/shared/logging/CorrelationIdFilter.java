package com.javy.backend.shared.logging;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements Filter {

    private static final String CORRELATION_ID = "correlationId";

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        try {
            String id = UUID.randomUUID().toString();
            MDC.put(CORRELATION_ID, id);

            chain.doFilter(request, response);

        } finally {
            MDC.remove(CORRELATION_ID);
        }
    }
}