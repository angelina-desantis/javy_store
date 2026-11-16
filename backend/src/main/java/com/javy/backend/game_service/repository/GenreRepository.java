package com.javy.backend.game_service.repository;

import com.javy.backend.game_service.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Optional<Genre> findByIgdbId(Integer igdbId);
}
