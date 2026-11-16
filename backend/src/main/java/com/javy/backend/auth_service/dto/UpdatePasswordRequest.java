package com.javy.backend.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request DTO used when an authenticated user wants to update their password.
 */
@Schema(
        name = "UpdatePasswordRequest",
        description = "Payload for updating an authenticated user's password."
)
public record UpdatePasswordRequest(
        @Schema(
                description = "The user's password. Must meet minimum security requirements.",
                example = "StrongPassword123!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Password is required.")
        @Size(min = 8, message = "Password must be at least 8 characters long.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#^()_+=<>]).{8,}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character."
        )
        String password
) {
}