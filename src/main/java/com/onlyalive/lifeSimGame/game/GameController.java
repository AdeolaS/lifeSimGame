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

    @PostMapping("/new-game")
    public ResponseEntity<Game> createGame(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Gender gender
            ) {
        return ResponseEntity.ok(gameService.createGame(firstName, lastName, gender));
    }

    @PutMapping("/{gameId}/age-up")
    public ResponseEntity<Game> advanceMonth(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameService.ageUp(gameId));
    }

//    @DeleteMapping("/{gameId}/delete-game")
//    public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
//        return ResponseEntity.ok().build();
//    }
}
