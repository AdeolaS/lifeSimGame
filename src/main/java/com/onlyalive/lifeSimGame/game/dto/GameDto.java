package com.onlyalive.lifeSimGame.game.dto;

import com.onlyalive.lifeSimGame.actor.dto.ActorFullDto;

import java.time.LocalDate;

public record GameDto(
        Long Id,
        LocalDate currentDateInGame,
        ActorFullDto player
) {
}
