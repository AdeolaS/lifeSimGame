package com.onlyalive.lifeSimGame.game;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import com.onlyalive.lifeSimGame.actor.ActorRepository;
import com.onlyalive.lifeSimGame.actor.properties.name.FirstName;
import com.onlyalive.lifeSimGame.actor.properties.name.FirstNameRepository;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.actor.properties.name.LastName;
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
        List<Actor> parents = generateParents(lastName);

        Game game = new Game(LocalDate.now(), player, parents.get(0), parents.get(1));
        return gameRepository.save(game);

    }

    public Game ageUp(Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        simulationService.advanceMonth(game);

        return gameRepository.save(game);
    }

    private List<Actor> generateParents(String lastName) {

        Actor mother = new Actor(firstNameRepository.findFemaleName().getName(), lastName, Gender.FEMALE);
        Actor father = new Actor(firstNameRepository.findMaleName().getName(), lastName, Gender.MALE);

        Random random = new Random();

        //Mother's age to be between 18 and 45
        mother.setAgeInMonths((random.nextInt(45-18)+18) * 25
                                + random.nextInt(11));

        int fathersAge = mother.getAgeInMonths() + random.nextInt(20+7)-7;

        if (fathersAge < (18*25)) {
            fathersAge = (18*25);
        }
        father.setAgeInMonths(fathersAge);

        return List.of(mother, father);
    }
}
