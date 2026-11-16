package com.javy.backend.auth_service.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a revoked JWT that must no longer be accepted.
 */
@Entity
@Getter
@Setter
@Table(name = "revoked_tokens")
public class RevokedToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, name = "token_hash")
    private String tokenHash;

    @Column(nullable = false, name = "expires_at")
    private Instant expiresAt;

    @Column(nullable = false, name = "invalidated_at")
    private Instant invalidatedAt;
}