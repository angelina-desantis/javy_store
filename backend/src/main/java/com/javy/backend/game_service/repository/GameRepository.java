package com.javy.backend.game_service.repository;

import com.javy.backend.game_service.model.Game;
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

    List<Game> findByName(String title);

    //return rows of games where they match every filter that is not null
    //if criteria is null, then it ignores that criteria
    @Query("""
            SELECT g.igdbId, t.name, p.name, gen.name
            FROM Game g
            JOIN genre_game gg ON g.game_id = gg.game_id
            JOIN platform_game pg ON g.game_id = pg.game_id
            JOIN theme_game tg ON g.game_id = tg.game_id
            WHERE (:genre IS NULL OR gen.genre = :genre)
              AND (:platform IS NULL OR plat.platform = :platform)
              AND (:theme IS NULL OR thme.theme = :theme)
            """)
    Page<Game> filterGames(
            @Param("genre") String genre,
            @Param("platform") String platform,
            @Param("theme") String theme,
            Pageable pageable
    );

}