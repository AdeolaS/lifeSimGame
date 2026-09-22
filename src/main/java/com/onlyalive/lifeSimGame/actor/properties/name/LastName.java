package com.onlyalive.lifeSimGame.actor.properties.name;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "last_names")
@Getter
@Setter
@NoArgsConstructor
public class LastName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "surname_type")
    private SurnameType surnameType;
}
