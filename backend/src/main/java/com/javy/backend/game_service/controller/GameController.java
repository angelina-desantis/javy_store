package com.javy.backend.game_service.controller;

import com.javy.backend.game_service.model.Game;
import com.javy.backend.game_service.scripts.dto.GameSearchResponse;
import com.javy.backend.game_service.service.GameService;
import com.javy.backend.shared.dto.ApiResult;
import com.javy.backend.shared.dto.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

    @Operation(
            summary = "Searches for a game",
            description = "Searches the database for the queried game and returns the game entity."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search successfully completed. Could return an empty " +
                    "array page if zero matches are found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "400", description = "Invalid search value")
    })
    //@RequestParam(required = false) --> make searching a game optional (then it will do nothing)
    @GetMapping("/search")
    public ResponseEntity<ApiResult<PaginatedResponse<GameSearchResponse>>> search(
            @RequestParam(required = false) String game, Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResult.success("Game Found", service.searchGame(game, pageable)));
    }

    //user selects a filtering option and frontend sends a string?
    //assumption: one value for each parameter
    @Operation (
            summary = "Find games by filtering",
            description = "Searches the database for games by selected filtering options"
    )
    @ApiResponses ({
            @ApiResponse(responseCode = "200", description = "Filter successfully completed"),
            @ApiResponse(responseCode = "400", description = "Invalid filter category")
    })
    @GetMapping("/filter")
    public ResponseEntity<ApiResult<PaginatedResponse<GameSearchResponse>>> filter (
                                            @RequestParam(required = false) String theme,
                                            @RequestParam(required = false) String platform,
                                            @RequestParam(required = false) String genre,
                                            Pageable pageable) {

        return ResponseEntity.ok(ApiResult.success("Filter Success", service.filter(theme, platform, genre, pageable)));
    }

}
