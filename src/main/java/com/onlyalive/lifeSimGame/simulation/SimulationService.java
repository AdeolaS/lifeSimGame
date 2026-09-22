package com.onlyalive.lifeSimGame.simulation;

import com.onlyalive.lifeSimGame.actor.Actor;
import com.onlyalive.lifeSimGame.actor.ActorRepository;
import com.onlyalive.lifeSimGame.game.Game;
import com.onlyalive.lifeSimGame.relationship.Relationship;
import com.onlyalive.lifeSimGame.relationship.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SimulationService {

    private final ActorRepository actorRepository;
    private final RelationshipRepository relationshipRepository;

    public void advanceTime(Game game) {

        int timeJump = 1;

        advanceCalendar(game, timeJump);

        ageAllCharacters(timeJump);

    }

    private void processRelationships() {

        List<Relationship> relationships = relationshipRepository.findAll();

        for (Relationship relationship : relationships) {
            relationship.setRelationshipRating(relationship.getRelationshipRating());
        }
    }

    private void advanceCalendar(Game game, int timeJump) {
        game.setCurrentDateInGame(game.getCurrentDateInGame().plusMonths(timeJump));
    }

    private void ageAllCharacters(int timeJump) {

        List<Actor> livingActors = actorRepository.findLivingActors();

        for (Actor actor : livingActors) {
            actor.setAgeInMonths(actor.getAgeInMonths() + timeJump);
        }
    }
}
