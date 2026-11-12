package com.javy.backend.user_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Represents a customer or an admin in this E-Commerce application.
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

    @Schema(description = "A user's unique identifier.")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Schema(description = "A user's email.")
    private String email;

    @Schema(description = "The user's hashed password.")
    private String password;

    @Schema(description = "The user's full name.")
    private String name;
}
