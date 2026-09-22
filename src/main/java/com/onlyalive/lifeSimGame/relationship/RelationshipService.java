package com.onlyalive.lifeSimGame.relationship;

import com.onlyalive.lifeSimGame.actor.Actor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RelationshipService {
    private final RelationshipRepository relationshipRepository;

    public void createRelationship(Actor actor, Actor relatedActor, RelationshipStatus status,
                                   RelationshipType relationshipType, int relationshipRating) {

        Relationship relationship = new Relationship(actor, relatedActor, status, relationshipType, relationshipRating);
        actor.addRelationship(relationship);
        relationshipRepository.save(relationship);

        Relationship oppositeRelationship = new Relationship(relatedActor, actor, status.getOpposite(actor.getGender()),
                relationshipType, relationshipRating);
        relatedActor.addRelationship(oppositeRelationship);
        relationshipRepository.save(oppositeRelationship);
    }

    public void generateParentRelationship(Actor mother, Actor father) {

        Random random = new Random();

        int roll = random.nextInt(100);

        if (roll < 50) {
            // 50%
            createRelationship(father, mother, RelationshipStatus.WIFE, RelationshipType.ROMANTIC, (random.nextInt(60)+40));
        } else if (roll < 75) {
            // 25%
            createRelationship(father, mother, RelationshipStatus.FIANCE, RelationshipType.ROMANTIC, (random.nextInt(60)+40));
        } else if (roll < 92) {
            // 17%
            createRelationship(father, mother, RelationshipStatus.LOVER, RelationshipType.ROMANTIC, (random.nextInt(60)+40));
        } else if (roll < 94) {
            // 2%
            createRelationship(father, mother, RelationshipStatus.EX_WIFE, RelationshipType.ROMANTIC, (random.nextInt(160)-80));
        } else if (roll < 96) {
            // 2%
            createRelationship(father, mother, RelationshipStatus.EX_FIANCE, RelationshipType.ROMANTIC, (random.nextInt(160)-80));
        } else if (roll < 98) {
            // 2%
            createRelationship(father, mother, RelationshipStatus.EX_LOVER, RelationshipType.ROMANTIC, (random.nextInt(160)-80));
        } else {
            // 2%
            createRelationship(father, mother, RelationshipStatus.STRANGER, RelationshipType.ROMANTIC, (random.nextInt(50)-25));
        }
    }

    @Transactional
    public void deleteAllRelationships() {
        relationshipRepository.deleteAll();
    }
}
