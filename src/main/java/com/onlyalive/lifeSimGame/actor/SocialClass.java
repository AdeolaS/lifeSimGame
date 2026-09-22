package com.onlyalive.lifeSimGame.actor;

import lombok.Getter;

@Getter
public enum SocialClass {
    PEASANT(0),
    COMMONER(1),
    MERCHANT(2),
    NOBLE(3),
    ROYALTY(4);

    private final int level;

    SocialClass(int level) {

        this.level = level;
    }
}