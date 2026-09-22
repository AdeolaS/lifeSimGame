package com.onlyalive.lifeSimGame.actor.properties.occupation;

import lombok.Getter;

@Getter
public enum EducationLevel {
    NONE(0),
    BASIC(1),
    APPRENTICESHIP(2),
    LITERATE(3),
    SCHOLARLY(4);

    private final int level;

    EducationLevel(int level) {

        this.level = level;
    }
}
