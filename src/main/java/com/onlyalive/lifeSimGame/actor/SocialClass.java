package com.onlyalive.lifeSimGame.actor;

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

    public int getSocialClass() {
        return level;
    }
}