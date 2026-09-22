package com.onlyalive.lifeSimGame.actor.dto;

import com.onlyalive.lifeSimGame.actor.SocialClass;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.actor.properties.occupation.EducationLevel;
import com.onlyalive.lifeSimGame.actor.properties.occupation.Occupation;
import com.onlyalive.lifeSimGame.relationship.dto.RelationshipDto;

import java.util.List;


public record ActorFullDto(
        Long actorId,
        String firstName,
        String lastName,
        int ageInMonths,
        Gender gender,
        SocialClass socialClass,
        EducationLevel educationLevel,
        Occupation occupation,
        boolean isAlive,
        List<RelationshipDto> relationships
) {
}
