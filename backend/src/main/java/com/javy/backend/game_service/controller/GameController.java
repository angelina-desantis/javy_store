package com.javy.backend.game_service.controller;

import com.javy.backend.game_service.model.Game;
import com.javy.backend.game_service.scripts.dto.GameSearchResponse;
import com.javy.backend.game_service.service.GameService;
import com.javy.backend.shared.dto.PaginatedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    //@RequestParam(required = false) --> make searching a game optional (then it will do nothing)
    @GetMapping("/search")
    public PaginatedResponse<GameSearchResponse> search(@RequestParam(required = false) String game, Pageable pageable) {
        return service.searchGame(game, pageable);
    }

    //user selects a filtering option and frontend sends a string?
    //assumption: one value for each parameter
    @GetMapping("/filter")
    public PaginatedResponse<GameSearchResponse> filter (
                                            @RequestParam(required = false) String theme,
                                            @RequestParam(required = false) String platform,
                                            @RequestParam(required = false) String genre,
                                            Pageable pageable) {
        return service.filter(theme, platform, genre, pageable);
    }

}
