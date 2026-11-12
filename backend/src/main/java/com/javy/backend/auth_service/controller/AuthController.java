package com.javy.backend.auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Handles authentication-related operations.
 */
@RestController
@RequestMapping("/auth")
@Tag(
        name = "Authentication",
        description = "Endpoints for authentication and session management"
)
public class AuthController {

    private final AuthService authService;
    private final CurrentUserService currentUserService;

    public AuthController(AuthService authService, CurrentUserService currentUserService) {
        this.authService = authService;
        this.currentUserService = currentUserService;
    }


    @Operation(
            summary = "Register a new user",
            description = """
                    Creates a new Supabase authentication account.
                    Users must confirm their email before logging in.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Signup successful"),
            @ApiResponse(responseCode = "409", description = "Email already registered"),
            @ApiResponse(responseCode = "502", description = "Supabase service unavailable")
    })
    @PostMapping("/signup")
    public ResponseEntity<ApiResult<Void>> signup(
            @Valid @RequestBody SignUpRequest request
    ) {
        authService.signup(request);

        return ResponseEntity.ok(
                ApiResult.success(
                        "Signup successful. Please confirm your email.",
                        null
                )
        );
    }


    @Operation(
            summary = "Login user",
            description = """
                    Authenticates a user through Supabase.
                    Returns access tokens and the application profile.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResult<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResult.success("Login successful.", response)
        );
    }


    @Operation(
            summary = "Refresh authentication token",
            description = """
                    Exchanges a valid Supabase refresh token
                    for a new access token and refresh token pair.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token")
    })
    @PostMapping("/refresh")
    public ResponseEntity<ApiResult<AuthResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        AuthResponse response = authService.refresh(request.refreshToken().trim());

        return ResponseEntity.ok(
                ApiResult.success("Token refreshed successfully.", response)
        );
    }

    @Operation(
            summary = "Resend email verification",
            description = """
                Requests another signup verification email through Supabase.
                Does not require authentication.
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verification request processed"),
            @ApiResponse(responseCode = "400", description = "Invalid email"),
            @ApiResponse(responseCode = "502", description = "Supabase service unavailable")
    })
    @PostMapping("/resend-verification")
    public ResponseEntity<ApiResult<Void>> resendVerificationEmail(
            @Valid @RequestBody EmailRequest request
    ) {
        authService.resendVerificationEmail(request.email().trim());

        return ResponseEntity.ok(
                ApiResult.success("Verification email sent successfully.", null)
        );
    }


    @Operation(
            summary = "Logout user",
            description = """
                Terminates the current Supabase session.
                Requires a valid bearer token in the Authorization header.
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid bearer token")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/logout")
    public ResponseEntity<ApiResult<Void>> logout(@AuthenticationPrincipal Jwt jwt) {
        authService.logout(jwt.getTokenValue());

        return ResponseEntity.ok(
                ApiResult.success("Logout successful.", null)
        );
    }


    @Operation(
            summary = "Delete user account",
            description = """
                Deletes the authenticated user's Supabase account.
                Requires a valid Supabase bearer token.
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid bearer token"),
            @ApiResponse(responseCode = "502", description = "Supabase service unavailable")
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/")
    public ResponseEntity<ApiResult<Void>> deleteAccount(
            @AuthenticationPrincipal Jwt jwt
    ) {
        UUID userId = currentUserService.extractUserId(jwt);

        authService.deleteAccount(userId);

        return ResponseEntity.ok(
                ApiResult.success("Account deleted successfully.", null)
        );
    }
}