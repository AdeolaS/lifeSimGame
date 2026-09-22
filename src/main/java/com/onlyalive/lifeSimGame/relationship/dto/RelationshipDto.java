package com.onlyalive.lifeSimGame.relationship.dto;

import com.onlyalive.lifeSimGame.actor.dto.ActorSummaryDto;
import com.onlyalive.lifeSimGame.relationship.RelationshipStatus;
import com.onlyalive.lifeSimGame.relationship.RelationshipType;

public record RelationshipDto(
        Long relationshipId,
        RelationshipStatus relationshipStatus,
        RelationshipType relationshipType,
        int relationshipRating,
        ActorSummaryDto relatedActor
) {
}
