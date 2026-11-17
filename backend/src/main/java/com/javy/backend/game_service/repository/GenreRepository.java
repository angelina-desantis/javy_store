package com.javy.backend.game_service.repository;

import com.javy.backend.game_service.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Optional<Genre> findByIgdbId(Integer igdbId);

    List<Genre> findAllByIgdbIdIn(Collection<Integer> igdbIds);
}
