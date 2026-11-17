package com.javy.backend.game_service.repository;

import com.javy.backend.game_service.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    Optional<Game> findByIgdbId(Integer igdbId);

    List<Game> findAllByIgdbIdIn(Collection<Integer> igdbIds);
}