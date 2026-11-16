package com.javy.backend.user_service.model;

import com.javy.backend.user_service.model.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents a user in the application.
 */
@Entity
@Getter
@Setter
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_email", columnNames = "email")
        }
)
@Schema(description = "Common user information across customers and admins.")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    @Schema(description = "A user's unique identifier.")
    private UUID id;

    @Column(nullable = false, name = "email")
    @Schema(description = "A user's email.")
    private String email;

    @Column(nullable = false, name = "hashed_password")
    @Schema(description = "The user's hashed password.")
    private String password;

    @Column(nullable = false, name = "first_name")
    @Schema(description = "The user's first name.")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    @Schema(description = "The user's last name.")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "user_type")
    @Schema(description = "The user's application role.")
    private Role role;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "The timestamp when the user account was created.")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Schema(description = "The timestamp when the user account was last updated.")
    private Instant updatedAt;

}