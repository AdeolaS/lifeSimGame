package com.onlyalive.lifeSimGame.simulation;

import com.onlyalive.lifeSimGame.actor.Actor;
import com.onlyalive.lifeSimGame.game.Game;
import org.springframework.stereotype.Service;

@Service
public class SimulationService {

    public void advanceMonth(Game game) {

        Actor player = game.getPlayer();

        // if the player is less than 2 years old, advance by 4 months instead of 1
        int timeSkip = (player.getAgeInMonths() < 24) ? 4 : 1;

        game.setCurrentDateInGame(game.getCurrentDateInGame().plusMonths(timeSkip));

        player.setAgeInMonths(player.getAgeInMonths() + timeSkip);
    }
}
