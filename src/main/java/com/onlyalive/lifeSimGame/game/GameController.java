package com.onlyalive.lifeSimGame.game;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;


@RestController 
@RequestMapping("/game")
@RequiredArgsConstructor 
public class GameController {

    private final GameService gameService;

    @PostMapping("/new-game")
    public Game createGame(
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName
    ) {
        return gameService.createGame(firstName, lastName);
    }

    @PutMapping("/next-month")
    public Game advanceMonth(@RequestParam Long gameId) {
        return gameService.advanceMonth(gameId);
    }
    
}
