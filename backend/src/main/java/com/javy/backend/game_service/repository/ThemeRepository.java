package com.javy.backend.game_service.repository;

import com.javy.backend.game_service.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ThemeRepository extends JpaRepository<Theme, UUID> {

    Optional<Theme> findByIgdbId(Integer igdbId);

    List<Theme> findAllByIgdbIdIn(Collection<Integer> igdbIds);
}
