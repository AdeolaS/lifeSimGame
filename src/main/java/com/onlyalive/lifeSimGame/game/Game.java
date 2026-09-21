package com.onlyalive.lifeSimGame.game;

import java.time.LocalDate;

import com.onlyalive.lifeSimGame.actor.Actor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@Entity
@Table(name = "games")
public class Game {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate currentDateInGame;

    // one Game has one Actor associated with it.
    // when certain operations are performed on Game, also perform them on the Actor
    @OneToOne(cascade = CascadeType.ALL)
    // the games table will include a column that points to the player's id
    @JoinColumn(name = "player_id")
    private Actor player;

    public Game(LocalDate currentDateInGame, Actor player) {
        this.currentDateInGame = currentDateInGame;
        this.player = player;
    }
}
