package com.javy.backend.game_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "game")
@Schema(description = "Represents a game from IGDB.")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Unique identifier for the game.", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Column(nullable = false, unique = true, name = "name")
    @Schema(description = "Name of the game.", example = "Minecraft")
    private String name;

    @Column(nullable = false, unique = true, name = "igdb_id")
    @Schema(description = "ID assigned by IGDB.", example = "1")
    private Integer igdbId;

    @Column(nullable = false, unique = true, name = "slug")
    @Schema(description = "Slug for the game", example = "minecraft")
    private String slug;

    @Column(name = "summary")
    @Schema(description = "A summary of the game.", example = "Minecraft is ...")
    private String summary;

    @Column(nullable = false, name = "image_url")
    @Schema(description = "URL of the game's cover image.", example = "https://images.igdb.com/igdb/image/upload/....png")
    private String imageUrl;

    @Column(name = "first_release_date")
    @Schema(description = "The date the game was first released.", example = "12938712")
    private Instant firstReleaseDate;

    @Column(name = "popularity_score")
    @Schema(description = "The game's popularity score from IGDB.", example = "0.003")
    private Double popularityScore;

    @ManyToMany
    @JoinTable(
            name = "genre_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "platform_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id")
    )
    private Set<Platform> platforms = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "theme_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private Set<Theme> themes = new HashSet<>();

}