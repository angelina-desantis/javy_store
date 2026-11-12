package com.javy.backend.shared.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Represents a paginated response containing a subset of results and pagination metadata.
 *
 * @param <T> the type of elements contained in the response
 * @param content the list of elements for the current page
 * @param number the zero-based index of the current page
 * @param size the requested page size
 * @param totalElements the total number of elements across all pages
 * @param totalPages the total number of available pages
 */
public record PaginatedResponse<T>(
        List<T> content,
        int number,
        int size,
        long totalElements,
        int totalPages
) {

    /**
     * Creates a paginated response from a Spring Data {@link Page}.
     *
     * @param <T> the type of elements contained in the page
     * @param page the Spring Data page to convert
     * @return a paginated response containing the page content and pagination metadata
     */
    public static <T> PaginatedResponse<T> from(Page<T> page) {
        return new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}