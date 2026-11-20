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

    @Query("""
        SELECT new com.javy.backend.dto.GameSearchResponse(g.id, g.name, g.slug, g.imageUrl)
        FROM Game g
        WHERE g.slug LIKE CONCAT('%', :slug, '%')
        ORDER BY g.name
    """)
    Page<Game> findBySlug(String slug, Pageable pageable);

    //return rows of games where they match every filter that is not null
    //if criteria is null, then it ignores that criteria
    @Query(value = """
            SELECT g.igdbId, t.name, p.name, gen.name
            FROM Game g
            JOIN genre_game gg ON gg.game_id = g.game_id
            JOIN genre gen ON gen.genre_id = gg.genre_id
            JOIN platform_game pg ON g.game_id = pg.game_id
            JOIN platform p ON p.platform_id = pg.platform_id
            JOIN theme_game tg ON g.game_id = tg.game_id
            JOIN theme t ON t.theme_id = tg.theme_id
            WHERE (:genre IS NULL OR gen.genre = :genre)
              AND (:platform IS NULL OR p.platform = :platform)
              AND (:theme IS NULL OR t.theme = :theme)
            """, nativeQuery = true)  //nativeQuery = true (run has raw SQL, default or false is JPQL)
    Page<Game> filterGames(
            @Param("genre") String genre,
            @Param("platform") String platform,
            @Param("theme") String theme,
            Pageable pageable
    );

}