package com.onlyalive.lifeSimGame.actor.properties.occupation;

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
    private Long id;

    @Enumerated(EnumType.STRING)
    private JobTitle jobTitle;

    private double salary;

    private boolean isLegal;

    public Occupation(JobTitle jobTitle, double salary, boolean isLegal) {

        this.jobTitle = jobTitle;
        this.salary = salary;
        this.isLegal = isLegal;
    }
}
