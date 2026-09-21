package com.onlyalive.lifeSimGame.relationship;

import com.onlyalive.lifeSimGame.actor.properties.Gender;

public enum RelationshipType {
    MOTHER,
    FATHER,
    DAUGHTER,
    SON,
    SISTER,
    BROTHER,
    GRANDMOTHER,
    GRANDFATHER,
    GRANDSON,
    GRANDDAUGHTER,
    AUNT,
    UNCLE,
    NIECE,
    NEPHEW,
    COUSIN,
    GIRLFRIEND,
    BOYFRIEND,
    EX_GIRLFRIEND,
    EX_BOYFRIEND,
    FIANCE,
    EX_FIANCE,
    HUSBAND,
    WIFE,
    EX_HUSBAND,
    EX_WIFE,
    FRIEND,
    BEST_FRIEND;

    public RelationshipType getOpposite(Gender gender) {
        return switch (this) {

            case MOTHER, FATHER -> gender == Gender.FEMALE ? DAUGHTER : SON;

            case DAUGHTER, SON -> gender == Gender.FEMALE ? MOTHER : FATHER;

            case SISTER, BROTHER -> gender == Gender.FEMALE ? SISTER : BROTHER;

            case GRANDMOTHER, GRANDFATHER -> gender == Gender.FEMALE ? GRANDDAUGHTER : GRANDSON;

            case GRANDDAUGHTER, GRANDSON -> gender == Gender.FEMALE ? GRANDMOTHER : GRANDFATHER;

            case AUNT, UNCLE -> gender == Gender.FEMALE ? NIECE : NEPHEW;

            case COUSIN -> COUSIN;

            case GIRLFRIEND, BOYFRIEND -> gender == Gender.FEMALE ? GIRLFRIEND : BOYFRIEND;

            case EX_GIRLFRIEND, EX_BOYFRIEND -> gender == Gender.FEMALE ? EX_GIRLFRIEND : EX_BOYFRIEND;

            case FIANCE -> FIANCE;

            case EX_FIANCE -> EX_FIANCE;

            case WIFE, HUSBAND -> gender == Gender.FEMALE ? WIFE : HUSBAND;

            case EX_WIFE, EX_HUSBAND -> gender == Gender.FEMALE ? EX_WIFE : EX_HUSBAND;

            case FRIEND -> FRIEND;

            case BEST_FRIEND -> BEST_FRIEND;

            default -> FRIEND;
        };
    }
}
