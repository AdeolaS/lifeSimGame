package com.onlyalive.lifeSimGame.game;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.onlyalive.lifeSimGame.actor.Actor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class GameService {

    private Actor player;
    
    public Game createGame(String firstName, String lastName) {
        
        if (firstName == null) {
            firstName = "John";
        }
        if (lastName == null) {
            lastName = "Smith";
        }

        player = new Actor(firstName, lastName);
        return new Game(LocalDate.now(), player);

    }

}
