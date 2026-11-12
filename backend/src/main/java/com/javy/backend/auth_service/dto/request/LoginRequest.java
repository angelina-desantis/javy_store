package com.javy.backend.auth_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Request payload used when an existing user attempts to login.

 * <p>
 * Validation ensures:
 * <ul>
 *     <li>Email is provided and follows a valid email format</li>
 *     <li>Password is not empty</li>
 * </ul>
 * </p>
 */
@Schema(
        name = "LoginRequest",
        description = "Payload for logging in."
)
public record LoginRequest(

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        @Pattern(
                regexp = "^[^\\s@]+@[^\\s@.]+(?:\\.[^\\s@.]+)+$",
                message = "Email must be valid."
        )
        @Schema(
                description = "The user's email address.",
                example = "jane.doe@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @NotBlank(message = "Please enter your password to login.")
        @Schema(
                description = "The user's password.",
                example = "StrongPassword123!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String password
) {
}
