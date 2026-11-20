package com.javy.backend.game_service.service;

import com.javy.backend.game_service.model.Game;
import com.javy.backend.game_service.repository.GameRepository;
import com.javy.backend.game_service.scripts.dto.GameSearchResponse;
import com.javy.backend.game_service.scripts.mapper.GameMapper;
import com.javy.backend.shared.dto.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    private final GameRepository repository;
    private final GameMapper mapper;

    public GameService(GameRepository repository, GameMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PaginatedResponse<GameSearchResponse> searchGame(String game, Pageable pageable) {
        String slug = slugify(game);
        //return empty page if nothing is searched
        if (slug == null || slug.isBlank()) {
            return PaginatedResponse.from(Page.<GameSearchResponse>empty(pageable));
        }
        //search by title/name
        Page<Game> result = repository.findBySlug(slug, pageable);
        Page<GameSearchResponse> formattedPage = result.map(mapper::toSearchDTO);
        return PaginatedResponse.from(formattedPage);
    }

    public PaginatedResponse<Game> filter(String genre, String platform, String theme, Pageable pageable) {
        //null strings are used by filterGames query in GameRepository
        //repository returns a page file
        Page<Game> result = repository.filterGames(
                blankToNull(genre),
                blankToNull(platform),
                blankToNull(theme), pageable);

        //copies the values from result page into a PaginatedResponse object
        return PaginatedResponse.from(result);
    }

    //helper method
    private String blankToNull (String str) {
        if (str == null || str.isBlank()) {
            return null;
        }
        return str;
    }

    //slugify user searches so it can be used for database search
    private String slugify(String str) {
        if (str == null || str.isBlank()) {
            return null;
        }
        return str.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").trim().replaceAll("[\\s-]+", "-");
    }


}
