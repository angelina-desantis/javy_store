package com.javy.backend.shared.dto;

import java.time.Instant;

/**
 * Standard API response wrapper used by all backend endpoints.
 * <p>The structure is: </p>
 * <ul>
 *     <li>Success status</li>
 *     <li>Message</li>
 *     <li>Optional data</li>
 *     <li>Timestamp when response was generated</li>
 * </ul>
 * <p>
 * This allows the frontend to handle responses regardless
 * of which service produced the response.
 * </p>
 *
 * @param <T> type of the response payload
 */
public record ApiResult<T>(
        boolean success,
        String message,
        T data,
        Instant timestamp
) {

    /**
     * Creates a successful API response.
     *
     * @param message message describing the successful operation
     * @param data    response payload
     * @param <T>     type of response payload
     * @return standardized successful API response
     */
    public static <T> ApiResult<T> success(String message, T data) {
        return new ApiResult<>(
                true,
                message,
                data,
                Instant.now()
        );
    }

    /**
     * Creates a failed API response without response data.
     *
     * @param message message describing the failure
     * @param <T>     type of response payload
     * @return standardized failed API response
     */
    public static <T> ApiResult<T> failure(String message) {
        return new ApiResult<>(
                false,
                message,
                null,
                Instant.now()
        );
    }
}