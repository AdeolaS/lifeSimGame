package com.onlyalive.lifeSimGame.actor;

import com.onlyalive.lifeSimGame.actor.properties.Gender;


public record ActorDto(
        Long id,
        String firstName,
        String lastName,
        int ageInMonths,
        Gender gender,
        boolean isAlive
) {
}
