package com.onlyalive.lifeSimGame.relationship.dto;

import com.onlyalive.lifeSimGame.actor.dto.ActorSummaryDto;
import com.onlyalive.lifeSimGame.relationship.RelationshipType;

public record RelationshipDto(
        Long Id,
        RelationshipType relationshipType,
        ActorSummaryDto relatedActor
) {
}
