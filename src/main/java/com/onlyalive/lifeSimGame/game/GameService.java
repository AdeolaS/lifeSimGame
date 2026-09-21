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
import com.onlyalive.lifeSimGame.relationship.RelationshipService;
import com.onlyalive.lifeSimGame.relationship.RelationshipType;
import com.onlyalive.lifeSimGame.simulation.SimulationService;
import org.springframework.stereotype.Service;

import com.onlyalive.lifeSimGame.actor.Actor;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor 
public class GameService {

    private final GameRepository gameRepository;
    private final FirstNameRepository firstNameRepository;
    private  final LastNameRepository lastNameRepository;
    private final SimulationService simulationService;
    private final RelationshipService relationshipService;

    @Transactional
    public Game createGame(String firstName, String lastName, Gender gender) {

        Actor player = generatePlayer(firstName, lastName, gender);

        List<Actor> parents = generateParents(player.getLastName());
        Actor mother = parents.get(0);
        Actor father = parents.get(1);

        relationshipService.createRelationship(player, mother, RelationshipType.MOTHER);
        relationshipService.createRelationship(player, father, RelationshipType.FATHER);

        if (player.getGender() == Gender.FEMALE) {
            relationshipService.createRelationship(mother, player, RelationshipType.DAUGHTER);
            relationshipService.createRelationship(father, player, RelationshipType.DAUGHTER);
        } else {
            relationshipService.createRelationship(mother, player, RelationshipType.SON);
            relationshipService.createRelationship(father, player, RelationshipType.SON);
        }


        Game game = new Game(LocalDate.now(), player, mother, father);
        return gameRepository.save(game);
    }

    public Game ageUp(Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        simulationService.advanceMonth(game);

        return gameRepository.save(game);
    }

    private  Actor generatePlayer(String firstName, String lastName, Gender gender) {

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

        return new Actor(firstName, lastName, gender);
    }

    private List<Actor> generateParents(String lastName) {

        Actor mother = new Actor(firstNameRepository.findFemaleName().getName(), lastName, Gender.FEMALE);
        Actor father = new Actor(firstNameRepository.findMaleName().getName(), lastName, Gender.MALE);

        Random random = new Random();

        //Mother's age to be between 18 and 45
        mother.setAgeInMonths((random.nextInt(28+18)) * 12
                                + random.nextInt(11));

        int fathersAge = mother.getAgeInMonths() + random.nextInt(20+7)-7;

        if (fathersAge < (18*25)) {
            fathersAge = (18*25);
        }
        father.setAgeInMonths(fathersAge);

        return List.of(mother, father);
    }
}
