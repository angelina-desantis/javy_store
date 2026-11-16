package com.javy.backend.user_service.controller;

import com.javy.backend.shared.dto.ApiResult;
import com.javy.backend.shared.exceptions.UnauthorizedException;
import com.javy.backend.user_service.dto.request.UpdateProfileRequest;
import com.javy.backend.user_service.dto.response.UserResponse;

import com.javy.backend.user_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "User Management",
        description = "Endpoints for managing authenticated user profiles."
)
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the authenticated user's profile information.
     *
     * @param userDetails the authenticated user's details
     * @return a standardized response containing the user's profile
     */
    @GetMapping("/me")
    @Operation(
            summary = "Get user profile",
            description = "Retrieves the profile information of the currently authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User profile successfully retrieved."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing, invalid, expired, or revoked JWT."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found."
            )
    })
    public ResponseEntity<ApiResult<UserResponse>> getProfile(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserResponse response = userService.getProfile(userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResult.success("User profile successfully retrieved.", response)
        );
    }

    /**
     * Updates the authenticated user's profile information.
     *
     * @param request the profile fields to update
     * @param userDetails the authenticated user's details
     * @return a standardized response containing the updated user profile
     */
    @PutMapping("/me")
    @Operation(
            summary = "Update user profile",
            description = "Updates the authenticated user's profile information. Only non-null fields are updated."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User profile successfully updated."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid profile update request."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing, invalid, expired, or revoked JWT."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found."
            )
    })
    public ResponseEntity<ApiResult<UserResponse>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserResponse response = userService.updateProfile(
                userDetails.getUsername(),
                request
        );

        return ResponseEntity.ok(
                ApiResult.success("User profile successfully updated.", response)
        );
    }

    /**
     * Extracts the JWT from the request's Authorization header.
     *
     * @param request the HTTP request containing the Authorization header
     * @return the extracted JWT
     * @throws UnauthorizedException if the Authorization header is missing or malformed
     */
    private String extractToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")
                || authorization.substring(7).isBlank()) {
            throw new UnauthorizedException("A valid Bearer token is required.");
        }

        return authorization.substring(7);
    }
}