package com.onlyalive.lifeSimGame.game;

import java.time.LocalDate;
import java.util.Optional;

import com.onlyalive.lifeSimGame.actor.ActorRepository;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import org.springframework.stereotype.Service;

import com.onlyalive.lifeSimGame.actor.Actor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class GameService {

    private final GameRepository gameRepository;
    private final ActorRepository actorRepository;
    
    public Game createGame(String firstName, String lastName, Gender gender) {
        
        if (firstName == null) {
            firstName = "John";
        }
        if (lastName == null) {
            lastName = "Smith";
        }
        if (gender == null) {
            gender = Gender.MALE;
        }

        Actor player = new Actor(firstName, lastName, gender);
        actorRepository.save(player);

        Game game = new Game(LocalDate.now(), player);
        return gameRepository.save(game);

    }

    public Game ageUp(Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        game.setCurrentDateInGame(game.getCurrentDateInGame().plusMonths(1));

        Actor player = game.getPlayer();
        player.setAgeInMonths(player.getAgeInMonths() + 1);

        actorRepository.save(player);
        return gameRepository.save(game);
    }
}
