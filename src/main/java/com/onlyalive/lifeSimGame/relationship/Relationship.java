package com.onlyalive.lifeSimGame.relationship;

import com.onlyalive.lifeSimGame.actor.Actor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "relationships")
@Getter
@Setter
@NoArgsConstructor
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationshipId;

    @ManyToOne
    private Actor actor;

    @ManyToOne
    private Actor relatedActor;

    @Enumerated(EnumType.STRING)
    private RelationshipStatus relationshipStatus;

    private int relationshipRating = 0;

    public Relationship(
            Actor actor,
            Actor relatedActor,
            RelationshipStatus relationshipStatus,
            int relationshipRating) {

        this.actor = actor;
        this.relatedActor = relatedActor;
        this.relationshipStatus = relationshipStatus;
        this.relationshipRating = relationshipRating;
    }
}
