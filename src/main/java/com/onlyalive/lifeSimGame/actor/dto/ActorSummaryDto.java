package com.onlyalive.lifeSimGame.actor.dto;

import com.onlyalive.lifeSimGame.actor.properties.Gender;


public record ActorSummaryDto(
        Long actorId,
        String firstName,
        String lastName,
        int ageInMonths,
        Gender gender,
        boolean isAlive
) {
}
