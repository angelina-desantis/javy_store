package com.javy.backend.shared.exceptions;

/**
 * Exception thrown when an external dependency fails.
 * <p>
 * They are caused by an unavailable service, unexpected response, or communication failure.
 * </p>
 */
public class ExternalServiceException extends ApiException {

    /**
     * Creates a new external service exception.
     *
     * @param message description of the external service failure
     */
    public ExternalServiceException(String message) {
        super(message, "EXTERNAL_ERROR");
    }
}