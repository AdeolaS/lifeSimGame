package com.onlyalive.lifeSimGame.game.dto;

import com.onlyalive.lifeSimGame.actor.dto.ActorMapper;
import com.onlyalive.lifeSimGame.game.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMapper {

    private final ActorMapper actorMapper;

    public GameDto toDto(Game game) {

        return new GameDto(
                game.getGameId(),
                game.getCurrentDateInGame(),
                actorMapper.toDto(game.getPlayer())
        );
    }
}
