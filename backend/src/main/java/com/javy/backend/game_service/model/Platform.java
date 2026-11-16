package com.javy.backend.game_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "platform")
@Schema(description = "Represents a gaming platform supported by a game.")
public class Platform {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Unique identifier for the platform.", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Column(nullable = false, unique = true, name = "name")
    @Schema(description = "Name of the gaming platform.", example = "Xbox Series X")
    private String name;

    @Column(nullable = false, unique = true, name = "igdb_id")
    @Schema(description = "ID assigned by IGDB.", example = "508")
    private Integer igdbId;

    @Column(nullable = false, name = "image_url")
    @Schema(description = "URL of the platform's image.", example = "https://images.igdb.com/igdb/image/upload/t_logo_med/platform_logo.png")
    private String imageUrl;

}