package com.javy.backend.game_service.service;

import com.javy.backend.game_service.model.Game;
import com.javy.backend.game_service.repository.GameRepository;
import com.javy.backend.shared.dto.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public List<Game> searchGame(String game) {
        //return empty list if nothing is searched
        if (game == null || game.isBlank()) {
            return List.of();
        }
        //search by title/name
        return new ArrayList<>(repository.findByName(game));
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
        } else {
            return str;
        }
    }

}
