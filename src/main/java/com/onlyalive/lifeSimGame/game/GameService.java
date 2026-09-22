package com.onlyalive.lifeSimGame.game;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import com.onlyalive.lifeSimGame.actor.ActorRepository;
import com.onlyalive.lifeSimGame.actor.dto.ActorGenerator;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.relationship.RelationshipService;
import com.onlyalive.lifeSimGame.relationship.RelationshipStatus;
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
    private final ActorRepository actorRepository;
    private final ActorGenerator actorGenerator;

    private final SimulationService simulationService;
    private final RelationshipService relationshipService;



    @Transactional
    public Game createGame(String firstName, String lastName, Gender gender) {

        Actor player = actorGenerator.generatePlayer(firstName, lastName, gender);

        List<Actor> parents = actorGenerator.generateParents(player);
        Actor mother = parents.get(0);
        Actor father = parents.get(1);

        actorRepository.save(player);
        actorRepository.save(mother);
        actorRepository.save(father);

        Random random = new Random();

        relationshipService.createRelationship(
                player, mother, RelationshipStatus.MOTHER, RelationshipType.FAMILIAL, (random.nextInt(60)+40));
        relationshipService.createRelationship(
                player, father, RelationshipStatus.FATHER, RelationshipType.FAMILIAL, (random.nextInt(60)+40));

        relationshipService.generateParentRelationship(mother, father);

        Game game = new Game(LocalDate.now().minusYears(500), player);
        return gameRepository.save(game);
    }

    public Game ageUp(Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        simulationService.advanceMonth(game);

        return gameRepository.save(game);
    }

    @Transactional
    public void deleteAllGamesAndActors() {
        actorRepository.deleteAll();
        gameRepository.deleteAll();
    }

    public List<Actor> getAllCharacters() {
        return actorRepository.findAll();
    }
}
