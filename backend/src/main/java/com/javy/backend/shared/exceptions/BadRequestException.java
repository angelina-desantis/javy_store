package com.javy.backend.shared.exceptions;

/**
 * Exception thrown when a request is syntactically valid but
 * violates application business rules.
 */
public class BadRequestException extends ApiException {

    /**
     * Creates a bad request exception.
     *
     * @param message explanation of why the request is invalid
     */
    public BadRequestException(String message) {
        super(message, "BAD_REQUEST");
    }
}