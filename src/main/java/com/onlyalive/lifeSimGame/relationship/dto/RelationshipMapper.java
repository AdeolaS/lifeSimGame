package com.onlyalive.lifeSimGame.relationship.dto;

import com.onlyalive.lifeSimGame.actor.Actor;
import com.onlyalive.lifeSimGame.actor.dto.ActorSummaryDto;
import com.onlyalive.lifeSimGame.relationship.Relationship;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RelationshipMapper {

    public RelationshipDto toDto(Relationship relationship) {

        Actor relatedActor = relationship.getRelatedActor();

        ActorSummaryDto relatedActorDto =
                new ActorSummaryDto(
                        relatedActor.getId(),
                        relatedActor.getFirstName(),
                        relatedActor.getLastName(),
                        relatedActor.getAgeInMonths(),
                        relatedActor.getGender(),
                        relatedActor.isAlive()
                );

        return new RelationshipDto(
                relationship.getId(),
                relationship.getRelationshipType(),
                relatedActorDto
        );
    }
}
