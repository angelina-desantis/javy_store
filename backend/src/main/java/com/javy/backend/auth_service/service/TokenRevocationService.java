package com.javy.backend.auth_service.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javy.backend.auth_service.model.RevokedToken;
import com.javy.backend.auth_service.repository.RevokedTokenRepository;

/**
 * Handles JWT revocation and validation against revoked tokens.
 */
@Service
public class TokenRevocationService {

    private final RevokedTokenRepository revokedTokenRepository;
    private final JWTGenerator jwtGenerator;

    public TokenRevocationService(
            RevokedTokenRepository revokedTokenRepository,
            JWTGenerator jwtGenerator) {
        this.revokedTokenRepository = revokedTokenRepository;
        this.jwtGenerator = jwtGenerator;
    }

    /**
     * Revokes a JWT until its original expiration time.
     *
     * @param token the JWT to revoke
     */
    @Transactional
    public void revokeToken(String token) {
        String tokenHash = hashToken(token);

        if (revokedTokenRepository.existsByTokenHash(tokenHash)) {
            return;
        }

        RevokedToken revokedToken = new RevokedToken();
        revokedToken.setTokenHash(tokenHash);
        revokedToken.setExpiresAt(jwtGenerator.extractExpiration(token).toInstant());
        revokedToken.setInvalidatedAt(Instant.now());

        revokedTokenRepository.save(revokedToken);
    }

    /**
     * Deletes invalidated tokens that have passed their expiration time.
     */
    @Scheduled(
            initialDelay = 0,
            fixedRate = 3600000
    )
    @Transactional
    public void deleteExpiredTokens() {
        revokedTokenRepository.deleteExpiredTokens();
    }

    /**
     * Checks whether a JWT has been revoked.
     *
     * @param token the JWT to check
     * @return true if the token has been revoked
     */
    public boolean isTokenRevoked(String token) {
        return revokedTokenRepository.existsByTokenHash(hashToken(token));
    }

    /**
     * Creates a SHA-256 hash of a JWT.
     *
     * @param token the JWT to hash
     * @return the token's hexadecimal SHA-256 hash
     */
    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm unavailable", e);
        }
    }
}