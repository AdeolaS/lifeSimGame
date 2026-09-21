package com.onlyalive.lifeSimGame.relationship;

import com.onlyalive.lifeSimGame.actor.Actor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RelationshipService {
    private final RelationshipRepository relationshipRepository;

    public void createRelationship(Actor actor, Actor relatedActor, RelationshipStatus status) {

        Relationship relationship = new Relationship(actor, relatedActor, status);
        actor.addRelationship(relationship);
        relationshipRepository.save(relationship);

        Relationship oppositeRelationship = new Relationship(relatedActor, actor, status.getOpposite(actor.getGender()));
        relatedActor.addRelationship(oppositeRelationship);
        relationshipRepository.save(oppositeRelationship);
    }

    public void generateParentRelationship(Actor mother, Actor father) {

        Random random = new Random();

        int roll = random.nextInt(100);

        if (roll < 50) {
            // 50%
            createRelationship(father, mother, RelationshipStatus.WIFE);
        } else if (roll < 75) {
            // 25%
            createRelationship(father, mother, RelationshipStatus.FIANCE);
        } else if (roll < 92) {
            // 17%
            createRelationship(father, mother, RelationshipStatus.GIRLFRIEND);
        } else if (roll < 94) {
            // 2%
            createRelationship(father, mother, RelationshipStatus.EX_WIFE);
        } else if (roll < 96) {
            // 2%
            createRelationship(father, mother, RelationshipStatus.EX_FIANCE);
        } else if (roll < 98) {
            // 2%
            createRelationship(father, mother, RelationshipStatus.EX_GIRLFRIEND);
        } else {
            // 2%
            createRelationship(father, mother, RelationshipStatus.STRANGER);
        }
    }

    public void deleteAllRelationships() {
        relationshipRepository.deleteAll();
    }
}
