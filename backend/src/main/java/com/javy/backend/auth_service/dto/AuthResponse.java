package com.javy.backend.auth_service.dto;

import java.util.UUID;

import com.javy.backend.user_service.dto.response.UserResponse;
import com.javy.backend.user_service.model.User;
import com.javy.backend.user_service.model.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User response containing a user's basic profile details and their JWT.
 */
@Schema(description = "User profile information returned after authentication.")
public record AuthResponse(

        @Schema(description = "The embedded user object.")
        UserResponse user,

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
    public static AuthResponse from(String token, User user) {
        return new AuthResponse(
                UserResponse.from(user),
                token
        );
    }
}