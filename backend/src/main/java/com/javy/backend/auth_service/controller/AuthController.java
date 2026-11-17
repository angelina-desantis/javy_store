package com.javy.backend.auth_service.controller;

import com.javy.backend.auth_service.dto.AuthResponse;
import com.javy.backend.auth_service.dto.UpdatePasswordRequest;
import com.javy.backend.shared.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.javy.backend.auth_service.dto.LoginRequest;
import com.javy.backend.auth_service.dto.SignUpRequest;
import com.javy.backend.auth_service.service.AuthService;
import com.javy.backend.shared.dto.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

/**
 * Exposes endpoints for user registration, login, and logout.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for account registration and authentication.")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new customer account.
     *
     * @param request the registration details
     * @return the registered user's profile and JWT
     */
    @Operation(
            summary = "Register a new account",
            description = "Creates a new customer account and returns the user's profile and JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account successfully created."),
            @ApiResponse(responseCode = "400", description = "Invalid registration details."),
            @ApiResponse(responseCode = "409", description = "An account with this email already exists.")
    })
    @PostMapping("/signup")
    public ResponseEntity<ApiResult<AuthResponse>> signUp(
            @Valid @RequestBody SignUpRequest request
    ) {
        AuthResponse response = authService.signUp(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResult.success("Account successfully created.", response)
        );
    }

    /**
     * Authenticates an existing user.
     *
     * @param request the user's login credentials
     * @return the authenticated user's profile and JWT
     */
    @Operation(
            summary = "Log in",
            description = "Authenticates a user and returns their profile and JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully authenticated."),
            @ApiResponse(responseCode = "400", description = "Invalid login request."),
            @ApiResponse(responseCode = "401", description = "Invalid email or password.")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResult<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResult.success("Successfully authenticated.", response)
        );
    }

    /**
     * Logs out the currently authenticated user by revoking their JWT.
     *
     * @param request the HTTP request containing the Authorization header
     * @return a standardized response indicating successful logout
     */
    @Operation(
            summary = "Log out",
            description = "Revokes the currently authenticated user's JWT, invalidating the current session.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully logged out."),
            @ApiResponse(responseCode = "401", description = "Missing, invalid, expired, or revoked JWT.")
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiResult<Void>> logout(HttpServletRequest request) {
        String token = extractToken(request);

        authService.logout(token);

        return ResponseEntity.ok(
                ApiResult.success("Successfully logged out.", null)
        );
    }

    /**
     * Updates the password of the currently authenticated user and revokes their current JWT.
     *
     * @param request the new password
     * @param httpRequest the HTTP request containing the Authorization header
     * @param userDetails the currently authenticated user's details
     * @return a standardized response indicating successful password update
     */
    @Operation(
            summary = "Update password",
            description = "Updates the currently authenticated user's password and revokes their current JWT. The user must log in again with their new password.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password successfully updated. The current session has been revoked."),
            @ApiResponse(responseCode = "400", description = "The new password does not meet the required validation rules."),
            @ApiResponse(responseCode = "401", description = "Missing, invalid, expired, or revoked JWT."),
            @ApiResponse(responseCode = "404", description = "The authenticated user could not be found.")
    })
    @PutMapping("/password")
    public ResponseEntity<ApiResult<Void>> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest request,
            HttpServletRequest httpRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String token = extractToken(httpRequest);

        authService.updatePassword(request, userDetails.getUsername(),token);

        return ResponseEntity.ok(
                ApiResult.success(
                        "Password successfully updated. Please log in again.",
                        null
                )
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

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedException("A valid Bearer token is required.");
        }

        return authorization.substring(7);
    }
}