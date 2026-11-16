
package com.javy.backend.user_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request payload for updating a user's profile information.
 *
 * @param firstName the user's updated first name
 * @param lastName the user's updated last name
 */
@Schema(
        name = "UpdateProfileRequest",
        description = "Payload for updating an authenticated user's profile information."
)
public record UpdateProfileRequest(

        @Schema(
                description = "The user's updated first name.",
                example = "John"
        )
        String firstName,

        @Schema(
                description = "The user's updated last name.",
                example = "Doe"
        )
        String lastName
) {
}