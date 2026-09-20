package com.onlyalive.lifeSimGame.game;

import java.time.LocalDate;
import java.util.Optional;

import com.onlyalive.lifeSimGame.actor.ActorRepository;
import org.springframework.stereotype.Service;

import com.onlyalive.lifeSimGame.actor.Actor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class GameService {

    private final GameRepository gameRepository;
    private final ActorRepository actorRepository;
    
    public Game createGame(String firstName, String lastName) {
        
        if (firstName == null) {
            firstName = "John";
        }
        if (lastName == null) {
            lastName = "Smith";
        }

        Actor player = new Actor(firstName, lastName);
        actorRepository.save(player);

        Game game = new Game(LocalDate.now(), player);
        return gameRepository.save(game);

    }

    public Game advanceMonth(Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        game.setCurrentDateInGame(game.getCurrentDateInGame().plusMonths(1));

        Actor player = game.getPlayer();
        player.setAgeInMonths(player.getAgeInMonths() + 1);

        actorRepository.save(player);
        return gameRepository.save(game);
    }
}
