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
                        relatedActor.getActorId(),
                        relatedActor.getFirstName(),
                        relatedActor.getLastName(),
                        relatedActor.getAgeInMonths(),
                        relatedActor.getGender(),
                        relatedActor.getSocialClass(),
                        relatedActor.getEducationLevel(),
                        relatedActor.isAlive()
                );

        return new RelationshipDto(
                relationship.getRelationshipId(),
                relationship.getRelationshipStatus(),
                relationship.getRelationshipRating(),
                relatedActorDto
        );
    }
}
