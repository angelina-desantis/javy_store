package com.javy.backend.game_service.repository;

import com.javy.backend.game_service.model.Game;
import com.javy.backend.game_service.scripts.dto.GameSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    Optional<Game> findByIgdbId(Integer igdbId);

    List<Game> findAllByIgdbIdIn(Collection<Integer> igdbIds);

    //CONCAT allows for partial matches
    //'%' = 0 or more chars
    @Query("""
        SELECT g
        FROM Game g
        WHERE g.slug LIKE CONCAT('%', :slug, '%')
        ORDER BY g.name
    """)
    Page<Game> findBySlug(String slug, Pageable pageable);

    //return rows of games where they match every filter that is not null
    //if criteria is null, then it ignores that criteria
    @Query("""
    SELECT g FROM Game g
    WHERE (cast(:genre as string) IS NULL OR EXISTS (
            SELECT 1 FROM g.genres gen WHERE gen.name = :genre))
      AND (cast(:platform as string) IS NULL OR EXISTS (
            SELECT 1 FROM g.platforms p WHERE p.name = :platform))
      AND (cast(:theme as string) IS NULL OR EXISTS (
            SELECT 1 FROM g.themes t WHERE t.name = :theme))
    ORDER BY g.name
    """)  //nativeQuery = true (run has raw SQL, default or false is JPQL)
    Page<Game> filterGames(
            @Param("theme") String theme,
            @Param("platform") String platform,
            @Param("genre") String genre,
            Pageable pageable
    );

}