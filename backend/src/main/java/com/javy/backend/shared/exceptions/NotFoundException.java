package com.javy.backend.shared.exceptions;

/**
 * Exception thrown when a requested resource cannot be found.
 */
public class NotFoundException extends ApiException {
    /**
     * Creates a not found exception.
     *
     * @param message explanation of the missing resource
     */
    public NotFoundException(String message) {
        super(message, "NOT_FOUND");
    }
}