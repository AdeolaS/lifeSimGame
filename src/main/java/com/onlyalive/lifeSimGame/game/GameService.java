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
import com.onlyalive.lifeSimGame.actor.properties.occupation.JobTitle;
import com.onlyalive.lifeSimGame.actor.properties.occupation.Occupation;
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

    private final double MALE_BABY_CHANCE = 0.5;
    private final ActorRepository actorRepository;

    @Transactional
    public Game createGame(String firstName, String lastName, Gender gender) {

        Actor player = generatePlayer(firstName, lastName, gender);

        List<Actor> parents = generateParents(player);
        Actor mother = parents.get(0);
        Actor father = parents.get(1);

        actorRepository.save(player);
        actorRepository.save(mother);
        actorRepository.save(father);

        relationshipService.createRelationship(player, mother, RelationshipType.MOTHER);
        relationshipService.createRelationship(player, father, RelationshipType.FATHER);

        if (player.getGender() == Gender.FEMALE) {
            relationshipService.createRelationship(mother, player, RelationshipType.DAUGHTER);
            relationshipService.createRelationship(father, player, RelationshipType.DAUGHTER);
        } else {
            relationshipService.createRelationship(mother, player, RelationshipType.SON);
            relationshipService.createRelationship(father, player, RelationshipType.SON);
        }

        Game game = new Game(LocalDate.now(), player);
        return gameRepository.save(game);
    }

    public Game ageUp(Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        simulationService.advanceMonth(game);

        return gameRepository.save(game);
    }

    private Actor generatePlayer(String firstName, String lastName, Gender gender) {

        if (firstName == null) {
            FirstName nameFromDB = firstNameRepository.findRandomName();
            firstName = nameFromDB.getName();

            if (gender == null) {
                if (nameFromDB.isFemaleName() && nameFromDB.isMaleName()) {
                    gender = (Math.random() < MALE_BABY_CHANCE) ? Gender.FEMALE : Gender.MALE;
                } else if (nameFromDB.isFemaleName()) {
                    gender = Gender.FEMALE;
                } else {
                    gender = Gender.MALE;
                }
            }
        } else {
            if (gender == null) {
                gender = (Math.random() < MALE_BABY_CHANCE) ? Gender.FEMALE : Gender.MALE;
            }
        }

        if (lastName == null) {
            lastName = lastNameRepository.findRandomName().getName();
        }

        return new Actor(firstName, lastName, gender);
    }

    private List<Actor> generateParents(Actor player) {

        Actor mother = new Actor(firstNameRepository.findFemaleName().getName(), player.getLastName(), Gender.FEMALE);
        Actor father = new Actor(firstNameRepository.findMaleName().getName(), player.getLastName(), Gender.MALE);

        Random random = new Random();
        //Mother's age to be between 18 and 45
        mother.setAgeInMonths((random.nextInt(28)+18) * 12);
        father.setAgeInMonths(29*12);

        return List.of(mother,father);
    }
}
