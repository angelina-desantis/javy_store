package com.javy.backend.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Request DTO used when signing up for an account.
 */
@Schema(
        name = "SignUpRequest",
        description = "Payload for creating a new user account, including identity, credentials, and role."
)
public record SignUpRequest(

        @Schema(
                description = "The user's email address. Must be unique and valid.",
                example = "jane.doe@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        String email,

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
        String password,

        @Schema(
                description = "The user's first name.",
                example = "Jane",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "First name is required.")
        String firstName,

        @Schema(
                description = "The user's last name.",
                example = "Doe",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Last name is required.")
        String lastName
) {}
