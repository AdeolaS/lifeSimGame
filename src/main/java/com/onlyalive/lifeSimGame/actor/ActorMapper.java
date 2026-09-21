package com.onlyalive.lifeSimGame.actor;

import org.springframework.stereotype.Component;

@Component
public class ActorMapper {

    public ActorDto toDto(Actor actor) {

        return new ActorDto(
                actor.getId(),
                actor.getFirstName(),
                actor.getLastName(),
                actor.getAgeInMonths(),
                actor.getGender(),
                actor.isAlive()
        );
    }
}
