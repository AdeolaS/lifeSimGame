package com.onlyalive.lifeSimGame.relationship;

import com.onlyalive.lifeSimGame.actor.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    List<Relationship> findByActor(Actor actor);
}
