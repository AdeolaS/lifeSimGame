package com.onlyalive.lifeSimGame.game;

import com.onlyalive.lifeSimGame.actor.ActorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMapper {

    private final ActorMapper actorMapper;

    public GameDto toDto(Game game) {

        return new GameDto(
                game.getId(),
                game.getCurrentDateInGame(),
                actorMapper.toDto(game.getPlayer())
        );
    }
}
