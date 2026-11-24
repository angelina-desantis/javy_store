package com.javy.backend.game_service.service;

import com.javy.backend.game_service.model.Game;
import com.javy.backend.game_service.repository.GameRepository;
import com.javy.backend.game_service.scripts.dto.GameSearchResponse;
import com.javy.backend.game_service.scripts.mapper.GameMapper;
import com.javy.backend.shared.dto.PaginatedResponse;
import com.javy.backend.shared.service.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static com.javy.backend.shared.service.ServiceUtils.*;
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
        String slug = ServiceUtils.slugify(game);

        //return empty page if nothing is searched
        if (slug == null || slug.isBlank()) {
            return PaginatedResponse.from(Page.<GameSearchResponse>empty(pageable));
        }
        //search by title/name
        Page<Game> result = repository.findBySlug(slug, pageable);
        //format the page into game DTO
        Page<GameSearchResponse> formattedPage = result.map(mapper::toSearchDTO);
        //convert Page into PaginatedResponse
        return PaginatedResponse.from(formattedPage);
    }

    public PaginatedResponse<GameSearchResponse> filter(String theme, String platform, String genre, Pageable pageable) {
        //null strings are used by filterGames query in GameRepository

        Page<Game> result = repository.filterGames(
                ServiceUtils.blankToNull(theme),
                ServiceUtils.blankToNull(platform),
                ServiceUtils.blankToNull(genre),
                pageable);
        
        Page<GameSearchResponse> formattedPage = result.map(mapper::toSearchDTO);
        return PaginatedResponse.from(formattedPage);
    }

}
