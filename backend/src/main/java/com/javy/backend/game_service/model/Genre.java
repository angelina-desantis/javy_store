package com.javy.backend.game_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "genre")
@Schema(description = "Represents a genre a game can be categorized into.")
public class Genre {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Unique identifier for the genre.", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Column(nullable = false, unique = true, name = "name")
    @Schema(description = "Name of the genre.", example = "MOBA")
    private String name;

    @Column(nullable = false, unique = true, name = "igdb_id")
    @Schema(description = "ID assigned by IGDB.", example = "1")
    private Integer igdbId;

    @Column(nullable = false, unique = true, name = "slug")
    @Schema(description = "Slug for the genre", example = "point_and_click")
    private String slug;

}