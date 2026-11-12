package com.javy.backend.shared.exceptions;

/**
 * Exception thrown when an authenticated user does not have
 * permission to perform an operation.
 */
public class ForbiddenException extends ApiException {

    /**
     * Creates a forbidden exception.
     *
     * @param message explanation of why access is denied
     */
    public ForbiddenException(String message) {
        super(message, "FORBIDDEN");
    }
}