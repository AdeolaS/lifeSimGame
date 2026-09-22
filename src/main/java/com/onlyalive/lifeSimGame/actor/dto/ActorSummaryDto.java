package com.onlyalive.lifeSimGame.actor.dto;

import com.onlyalive.lifeSimGame.actor.SocialClass;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.actor.properties.occupation.EducationLevel;


public record ActorSummaryDto(
        Long actorId,
        String firstName,
        String lastName,
        int ageInMonths,
        Gender gender,
        SocialClass socialClass,
        EducationLevel educationLevel,
        boolean isAlive
) {
}
