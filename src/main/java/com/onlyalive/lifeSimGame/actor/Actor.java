package com.onlyalive.lifeSimGame.actor;

import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.relationship.Relationship;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "actors")
public class Actor {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;

    @Builder.Default
    private int ageInMonths = 0;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder.Default
    private boolean isAlive = true;

//    @ManyToOne
//    private Occupation occupation;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Relationship> relationships = new ArrayList<>();

    public Actor(String firstName, String lastName, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }
}
