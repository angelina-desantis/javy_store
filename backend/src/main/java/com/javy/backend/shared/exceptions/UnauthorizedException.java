package com.javy.backend.shared.exceptions;

/**
 * Exception thrown when authentication is required or invalid.
 *
 * <p>
 * Used for cases such as missing or expired credentials.
 * </p>
 */
public class UnauthorizedException extends ApiException {
    /**
     * Creates an unauthorized exception.
     *
     * @param message explanation of authentication failure
     */
    public UnauthorizedException(String message) {
        super(message, "UNAUTHORIZED");
    }
}