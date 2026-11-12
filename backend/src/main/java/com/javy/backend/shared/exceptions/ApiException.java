package com.javy.backend.shared.exceptions;

import lombok.Getter;

/**
 * Base exception class for all application-specific exceptions.
 *
 * <p>
 * All custom exceptions extend this class so that they can be handled
 * consistently by the global exception handler.
 * </p>
 */
@Getter
public abstract class ApiException extends RuntimeException {

    /**
     * Machine-readable error identifier.
     */
    private final String code;

    /**
     * Creates an application exception.
     *
     * @param message human-readable error message
     * @param code machine-readable error code
     */
    protected ApiException(String message, String code) {
        super(message);
        this.code = code;
    }
}