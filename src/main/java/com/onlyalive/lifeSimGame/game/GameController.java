package com.onlyalive.lifeSimGame.game;

import com.onlyalive.lifeSimGame.actor.properties.Gender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;


@RestController 
@RequestMapping("/game")
@RequiredArgsConstructor 
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    @PostMapping("/new-game")
    public ResponseEntity<GameDto> createGame(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Gender gender
    ) {
        Game game = gameService.createGame(firstName, lastName, gender);
        return ResponseEntity.ok(gameMapper.toDto(game));
    }

    @PutMapping("/{gameId}/age-up")
    public ResponseEntity<Game> advanceMonth(@PathVariable Long gameId) {

        Game game = gameService.ageUp(gameId);
        return ResponseEntity.ok(game);
    }
}
