package com.onlyalive.lifeSimGame.event;

import com.onlyalive.lifeSimGame.actor.Actor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "game_events")
@Getter
@Setter
@NoArgsConstructor
public class GameEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String title;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    private Actor actor;

    public GameEvent(
            LocalDate date,
            String title,
            String description,
            EventType eventType,
            Actor actor) {

        this.date = date;
        this.title = title;
        this.description = description;
        this.eventType = eventType;
        this.actor = actor;
    }
}
