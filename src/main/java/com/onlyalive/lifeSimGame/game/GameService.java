package com.onlyalive.lifeSimGame.game;

import java.time.LocalDate;

import com.onlyalive.lifeSimGame.actor.ActorRepository;
import com.onlyalive.lifeSimGame.actor.properties.name.FirstName;
import com.onlyalive.lifeSimGame.actor.properties.name.FirstNameRepository;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.actor.properties.name.LastNameRepository;
import com.onlyalive.lifeSimGame.simulation.SimulationService;
import org.springframework.stereotype.Service;

import com.onlyalive.lifeSimGame.actor.Actor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class GameService {

    private final GameRepository gameRepository;
    private final ActorRepository actorRepository;
    private final FirstNameRepository firstNameRepository;
    private  final LastNameRepository lastNameRepository;
    private final SimulationService simulationService;
    
    public Game createGame(String firstName, String lastName, Gender gender) {

        if (firstName == null) {
            FirstName nameFromDB = firstNameRepository.findRandomName();
            firstName = nameFromDB.getName();

            if (gender == null) {
                if (nameFromDB.isFemaleName() && nameFromDB.isMaleName()) {
                    gender = (Math.random() < 0.5) ? Gender.FEMALE : Gender.MALE;
                } else if (nameFromDB.isFemaleName()) {
                    gender = Gender.FEMALE;
                } else {
                    gender = Gender.MALE;
                }
            }
        } else {
            if (gender == null) {
                gender = (Math.random() < 0.5) ? Gender.FEMALE : Gender.MALE;
            }
        }

        if (lastName == null) {
            lastName = lastNameRepository.findRandomName().getName();
        }

        Actor player = new Actor(firstName, lastName, gender);
        actorRepository.save(player);

        Game game = new Game(LocalDate.now(), player);
        return gameRepository.save(game);

    }

    public Game ageUp(Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        simulationService.advanceMonth(game);

        return gameRepository.save(game);
    }
}
