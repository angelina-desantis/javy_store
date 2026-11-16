package com.javy.backend.game_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "theme")
@Schema(description = "Represents a theme a game can be categorized into.")
public class Theme {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Unique identifier for the theme.", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Column(nullable = false, unique = true, name = "name")
    @Schema(description = "Name of the theme.", example = "Horror")
    private String name;

    @Column(nullable = false, unique = true, name = "igdb_id")
    @Schema(description = "ID assigned by IGDB.", example = "1")
    private Integer igdbId;

    @Column(nullable = false, unique = true, name = "slug")
    @Schema(description = "Slug for the genre", example = "horror")
    private String slug;

}
