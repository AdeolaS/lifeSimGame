package com.onlyalive.lifeSimGame.game;

import java.time.LocalDate;

public record GameDto(
        Long Id,
        LocalDate currentDateInGame,
        com.onlyalive.lifeSimGame.actor.ActorDto player
) {
}
