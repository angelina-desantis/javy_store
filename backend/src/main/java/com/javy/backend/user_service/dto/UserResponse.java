package com.javy.backend.user_service.dto;

import java.util.UUID;

import com.javy.backend.user_service.model.User;
import com.javy.backend.user_service.model.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User response containing a user's basic profile details and their JWT.
 */
@Schema(description = "User profile information returned after authentication.")
public record UserResponse(

        @Schema(description = "The user's unique identifier.")
        UUID id,

        @Schema(description = "The user's email address.")
        String email,

        @Schema(description = "The user's first name.")
        String firstName,

        @Schema(description = "The user's last name.")
        String lastName,

        @Schema(description = "The user's application role.")
        Role role,

        @Schema(description = "The user's JWT authentication token.")
        String token

) {

    /**
     * Creates a user response from a user entity and JWT.
     *
     * @param token the user's JWT authentication token
     * @param user the user entity
     * @return the user response
     */
    public static UserResponse from(String token, User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                token
        );
    }
}