package com.onlyalive.lifeSimGame.actor.dto;

import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.relationship.dto.RelationshipDto;

import java.util.List;


public record ActorFullDto(
        Long id,
        String firstName,
        String lastName,
        int ageInMonths,
        Gender gender,
        boolean isAlive,
        List<RelationshipDto> relationships
) {
}
