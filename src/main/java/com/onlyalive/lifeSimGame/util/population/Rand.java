package com.onlyalive.lifeSimGame.util.population;

import com.onlyalive.lifeSimGame.actor.SocialClass;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.actor.properties.occupation.EducationLevel;

import java.util.Random;

public class Rand {

    private static final Random random = new Random();
    private static int roll;

    public static Gender generateGender() {

        roll = random.nextInt(100);

        return (roll < 50) ? Gender.FEMALE : Gender.MALE;
    }

    public static int generateAgeInMonths (int minYears, int maxYears) {

        roll = random.nextInt(minYears, maxYears);

        return (roll * 12) + random.nextInt(12);
    }

    public static SocialClass generateSocialClass() {

        roll = random.nextInt(100);

        if (roll < 45) {
            // 45% Peasants
            return SocialClass.PEASANT;
        } else if (roll < 85) {
            // 40% Commoners
            return SocialClass.COMMONER;
        } else if (roll < 95) {
            // 10% Merchants
            return SocialClass.MERCHANT;
        } else if (roll < 99) {
            // 4% Nobles
            return SocialClass.NOBLE;
        } else {
            // 1% Royals
            return SocialClass.ROYALTY;
        }
    }

    public static EducationLevel generateEducationLevel(SocialClass socialClass) {

        roll = random.nextInt(100);

        if (socialClass == SocialClass.PEASANT) {
            if (roll < 90) {
                // 90% None
                return EducationLevel.NONE;
            } else {
                // 10% Basic
                return EducationLevel.BASIC;
            }
        }

        if (socialClass == SocialClass.COMMONER) {
            if (roll < 10) {
                // 10% None
                return EducationLevel.NONE;
            } else if (roll < 40) {
                // 30% Basic
                return EducationLevel.BASIC;
            } else if (roll < 80) {
                // 40% Apprenticeship
                return EducationLevel.APPRENTICESHIP;
            } else if (roll < 95) {
                // 15% Literate
                return EducationLevel.LITERATE;
            } else {
                // 5% Scholarly
                return EducationLevel.SCHOLARLY;
            }
        }

        if (socialClass == SocialClass.MERCHANT) {
            if (roll < 20) {
                // 20% Basic
                return EducationLevel.BASIC;
            } else if (roll < 60) {
                // 40% Apprenticeship
                return EducationLevel.APPRENTICESHIP;
            } else if (roll < 85) {
                // 25% Literate
                return EducationLevel.LITERATE;
            } else {
                // 15% Scholarly
                return EducationLevel.SCHOLARLY;
            }
        }

        if (socialClass == SocialClass.NOBLE) {
            if (roll < 5) {
                // 5% Basic
                return EducationLevel.BASIC;
            } else if (roll < 10) {
                // 5% Apprenticeship
                return EducationLevel.APPRENTICESHIP;
            } else if (roll < 45) {
                // 35% Literate
                return EducationLevel.LITERATE;
            } else {
                // 55% Scholarly
                return EducationLevel.SCHOLARLY;
            }
        }

        if (socialClass == SocialClass.ROYALTY) {
            if (roll < 50) {
                // 50% Literate
                return EducationLevel.LITERATE;
            } else {
                // 50% Scholarly
                return EducationLevel.SCHOLARLY;
            }
        }

        return EducationLevel.NONE;
    }
}
