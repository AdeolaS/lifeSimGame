package com.onlyalive.lifeSimGame.actor.dto;

import com.onlyalive.lifeSimGame.actor.Actor;
import com.onlyalive.lifeSimGame.actor.SocialClass;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.actor.properties.name.FirstName;
import com.onlyalive.lifeSimGame.actor.properties.name.FirstNameRepository;
import com.onlyalive.lifeSimGame.actor.properties.name.LastName;
import com.onlyalive.lifeSimGame.actor.properties.name.LastNameRepository;
import com.onlyalive.lifeSimGame.actor.properties.occupation.EducationLevel;
import com.onlyalive.lifeSimGame.actor.properties.occupation.Occupation;
import com.onlyalive.lifeSimGame.actor.properties.occupation.OccupationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ActorGenerator {

    private final FirstNameRepository firstNameRepository;
    private final LastNameRepository lastNameRepository;
    private final OccupationRepository occupationRepository;

    private static final Random random = new Random();
    private static int roll;

    public Actor generatePlayer(String firstName, String lastName, Gender gender) {

        SocialClass socialClass = generateSocialClass();

        if (lastName == null) {
            lastName = generateLastName(socialClass).getName();
        }

        if (firstName == null) {
            FirstName nameFromDB = firstNameRepository.findRandomName();
            firstName = nameFromDB.getName();

            if (gender == null) {
                // if the name is unisex
                if (nameFromDB.isFemaleName() && nameFromDB.isMaleName()) {
                    gender = generateGender();

                } else if (nameFromDB.isFemaleName()) {
                    gender = Gender.FEMALE;
                } else {
                    gender = Gender.MALE;
                }
            }
        } else {
            if (gender == null) {
                gender = generateGender();
            }
        }

        Actor player = new Actor(firstName, lastName, gender);
        player.setPlayer(true);
        player.setSocialClass(socialClass);

        return player;
    }

    public List<Actor> generateParents(Actor player) {

        SocialClass socialClass = player.getSocialClass();

        int playerAgeInYears = player.getAgeInMonths()/12;

        Actor mother = new Actor(
                firstNameRepository.findFemaleName().getName(),
                player.getLastName(),
                generateAgeInMonths(playerAgeInYears + 16, playerAgeInYears + 40),
                Gender.FEMALE,
                socialClass,
                generateEducationLevel(socialClass));

        Actor father = new Actor(
                firstNameRepository.findMaleName().getName(),
                player.getLastName(),
                generateAgeInMonths(playerAgeInYears + 16, playerAgeInYears + 55),
                Gender.MALE,
                socialClass,
                generateEducationLevel(socialClass));

        Occupation jobMother = generateJob(mother.getAgeInMonths(), socialClass, mother.getEducationLevel());
        Occupation jobFather = generateJob(father.getAgeInMonths(), socialClass, father.getEducationLevel());

        mother.setOccupation(jobMother);
        father.setOccupation(jobFather);

        return List.of(mother,father);
    }

    private LastName generateLastName(SocialClass socialClass) {

        if (socialClass == SocialClass.NOBLE || socialClass == SocialClass.ROYALTY) {

            return lastNameRepository.findRandomNobleName();
        } else {
            return lastNameRepository.findRandomName();
        }
    }

    private Gender generateGender() {

        roll = random.nextInt(100);

        return (roll < 50) ? Gender.FEMALE : Gender.MALE;
    }

    private int generateAgeInMonths (int minYears, int maxYears) {

        roll = random.nextInt(minYears, maxYears);

        return (roll * 12) + random.nextInt(12);
    }

    private SocialClass generateSocialClass() {

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

    private EducationLevel generateEducationLevel(SocialClass socialClass) {

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

    private Occupation generateJob(int ageInMonths, SocialClass socialClass, EducationLevel educationLevel) {

        List<Occupation> availableJobs = occupationRepository.findAll()
                .stream()
                .filter(job -> job.getMinimumAgeInMonths() <= ageInMonths)
                .filter(job -> job.getMinimumSocialClass().getLevel() <= socialClass.getLevel())
                .filter(job -> job.getRequiredEducation().getLevel() <= educationLevel.getLevel())
                .toList();

        if (availableJobs.isEmpty()) {
            return null;
        }
        return availableJobs.get(new Random().nextInt(availableJobs.size()));
    }
}
