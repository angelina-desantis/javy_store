package com.javy.backend.auth_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javy.backend.auth_service.model.RevokedToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Provides database operations for revoked JWTs.
 */
public interface RevokedTokenRepository extends JpaRepository<RevokedToken, UUID> {

    boolean existsByTokenHash(String tokenHash);

    @Modifying
    @Query("DELETE FROM RevokedToken t WHERE t.expiresAt <= CURRENT_TIMESTAMP")
    void deleteExpiredTokens();
}