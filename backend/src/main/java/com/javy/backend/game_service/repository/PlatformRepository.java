package com.javy.backend.game_service.repository;

import com.javy.backend.game_service.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlatformRepository extends JpaRepository<Platform, UUID> {

    Optional<Platform> findByIgdbId(Integer igdbId);

    List<Platform> findAllByIgdbIdIn(Collection<Integer> igdbIds);

}
