package com.onlyalive.lifeSimGame.game;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController 
@RequestMapping("/game")
@RequiredArgsConstructor 
public class GameController {

    private final GameService gameService;

    @PostMapping("/new")
    public Game createGame(
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName
    ) {
        return gameService.createGame(firstName, lastName);
    }
    
}
