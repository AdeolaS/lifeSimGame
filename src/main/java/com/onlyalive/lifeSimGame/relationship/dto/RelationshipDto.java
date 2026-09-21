package com.onlyalive.lifeSimGame.relationship.dto;

import com.onlyalive.lifeSimGame.actor.dto.ActorSummaryDto;
import com.onlyalive.lifeSimGame.relationship.RelationshipStatus;

public record RelationshipDto(
        Long relationshipId,
        RelationshipStatus relationshipStatus,
        ActorSummaryDto relatedActor
) {
}
