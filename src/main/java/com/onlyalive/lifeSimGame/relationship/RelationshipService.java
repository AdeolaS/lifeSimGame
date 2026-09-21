package com.onlyalive.lifeSimGame.relationship;

import com.onlyalive.lifeSimGame.actor.Actor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationshipService {
    private final RelationshipRepository relationshipRepository;

    public void createRelationship(Actor actor, Actor relatedActor, RelationshipType type) {

        Relationship relationship =
                new Relationship(actor, relatedActor, type);

        relationshipRepository.save(relationship);
    }
}
