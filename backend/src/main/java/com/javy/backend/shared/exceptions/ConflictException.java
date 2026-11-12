package com.javy.backend.shared.exceptions;

/**
 * Exception thrown when a request conflicts with existing data.
 *<p>An example could be attempts to create duplicate records.</p>
 */
public class ConflictException extends ApiException {

    /**
     * Creates a conflict exception.
     *
     * @param message explanation of the conflict
     */
    public ConflictException(String message) {
        super(message, "CONFLICT");
    }
}