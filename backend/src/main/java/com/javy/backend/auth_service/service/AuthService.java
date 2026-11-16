package com.javy.backend.auth_service.service;

import com.javy.backend.auth_service.dto.UpdatePasswordRequest;
import com.javy.backend.shared.exceptions.BadRequestException;
import com.javy.backend.shared.exceptions.ConflictException;
import com.javy.backend.shared.exceptions.NotFoundException;
import com.javy.backend.shared.exceptions.UnauthorizedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javy.backend.auth_service.dto.LoginRequest;
import com.javy.backend.auth_service.dto.SignUpRequest;
import com.javy.backend.user_service.dto.UserResponse;
import com.javy.backend.user_service.model.User;
import com.javy.backend.user_service.model.enums.Role;
import com.javy.backend.user_service.repository.UserRepository;

/**
 * Handles user registration, authentication, and logout.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final TokenRevocationService tokenRevocationService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JWTGenerator jwtGenerator,
            TokenRevocationService tokenRevocationService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.tokenRevocationService = tokenRevocationService;
    }

    /**
     * Registers a new customer account and generates an authentication token.
     *
     * @param request the registration details
     * @return the newly registered user's profile and JWT
     */
    @Transactional
    public UserResponse signUp(SignUpRequest request) {
        String email = request.email().trim().toLowerCase();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new ConflictException("An account with this email already exists.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName().trim());
        user.setLastName(request.lastName().trim());
        user.setRole(Role.CUSTOMER);

        user = userRepository.save(user);

        String token = jwtGenerator.generateToken(user.getEmail());

        return UserResponse.from(token, user);
    }

    /**
     * Authenticates an existing user and generates an authentication token.
     *
     * @param request the user's login credentials
     * @return the authenticated user's profile and JWT
     */
    public UserResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.password()
                )
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials."));

        String token = jwtGenerator.generateToken(user.getEmail());

        return UserResponse.from(token, user);
    }

    /**
     * Revokes an authenticated user's JWT.
     *
     * @param token the JWT to revoke
     */
    public void logout(String token) {
        if (token == null || token.isBlank() || !jwtGenerator.validateToken(token)) {
            throw new UnauthorizedException("Invalid or expired authentication token.");
        }

        tokenRevocationService.revokeToken(token);
    }

    /**
     * Updates the password of the specified authenticated user and revokes their current JWT.
     *
     * @param request the new password
     * @param email the email of the currently authenticated user
     * @param token the currently authenticated user's JWT to revoke
     * @throws NotFoundException if the user does not exist
     * @throws UnauthorizedException if the JWT is invalid or expired
     * @throws BadRequestException if the new password matches the current password
     */
    @Transactional
    public void updatePassword(UpdatePasswordRequest request, String email, String token) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));

        if (passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadRequestException("New password must be different from the current password.");
        }

        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        logout(token);
    }
}