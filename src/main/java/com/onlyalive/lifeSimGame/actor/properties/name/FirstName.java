package com.onlyalive.lifeSimGame.actor.properties.name;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "first_names")
@Getter
@Setter
@NoArgsConstructor
public class FirstName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "is_male_name")
    private boolean isMaleName;

    @Column(name = "is_female_name")
    private boolean isFemaleName;
}
