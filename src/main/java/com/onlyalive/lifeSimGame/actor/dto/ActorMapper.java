package com.onlyalive.lifeSimGame.actor.dto;

import com.onlyalive.lifeSimGame.actor.Actor;
import com.onlyalive.lifeSimGame.relationship.dto.RelationshipDto;
import com.onlyalive.lifeSimGame.relationship.dto.RelationshipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor

public class ActorMapper {

    private final RelationshipMapper relationshipMapper;

    public ActorFullDto toDto(Actor actor) {

        List<RelationshipDto> relationships =
                actor.getRelationships()
                        .stream()
                        .map(relationshipMapper::toDto)
                        .toList();

        return new ActorFullDto(
                actor.getActorId(),
                actor.getFirstName(),
                actor.getLastName(),
                actor.getAgeInMonths(),
                actor.getGender(),
                actor.getSocialClass(),
                actor.getEducationLevel(),
                actor.getOccupation(),
                actor.isAlive(),
                relationships
        );
    }
}