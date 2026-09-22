package com.onlyalive.lifeSimGame.actor.properties.occupation;

import com.onlyalive.lifeSimGame.actor.SocialClass;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "occupations")
@Getter
@Setter
@NoArgsConstructor
public class Occupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long occupationId;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "monthly_wage")
    private double monthlyWage;

    @Column(name = "is_legal")
    private boolean isLegal;
/**
    0   = virtually no social prestige
    25  = ordinary
    50  = respectable
    75  = highly respected
    100 = extremely prestigious
 **/
    private int prestige;
/**
    0   = essentially safe
    25  = minor risk
    50  = moderate risk
    75  = dangerous
    100 = extremely dangerous
 **/
    private int danger;

    @Column(name = "minimum_age_in_months")
    private int minimumAgeInMonths;

    @Column(name = "minimum_social_class")
    @Enumerated(EnumType.STRING)
    private SocialClass minimumSocialClass;

    @Column(name = "required_education")
    @Enumerated(EnumType.STRING)
    private EducationLevel requiredEducation;

    public Occupation(String jobTitle,
                      double monthlyWage,
                      boolean isLegal,
                      int prestige,
                      int danger,
                      int minimumAgeInMonths,
                      SocialClass minimumSocialClass,
                      EducationLevel requiredEducation) {

        this.jobTitle = jobTitle;
        this.monthlyWage = monthlyWage;
        this.isLegal = isLegal;
        this.prestige = prestige;
        this.danger = danger;
        this.minimumAgeInMonths = minimumAgeInMonths;
        this.minimumSocialClass = minimumSocialClass;
        this.requiredEducation = requiredEducation;
    }
}
