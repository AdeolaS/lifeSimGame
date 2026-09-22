package com.onlyalive.lifeSimGame.actor;

import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.actor.properties.occupation.EducationLevel;
import com.onlyalive.lifeSimGame.actor.properties.occupation.Occupation;
import com.onlyalive.lifeSimGame.relationship.Relationship;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter 
@Setter 
@NoArgsConstructor
@Entity
@Table(name = "actors")
public class Actor {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorId;

    private boolean isPlayer = false;
    
    private String firstName;
    private String lastName;

    private int ageInMonths = 0;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SocialClass socialClass;

    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel = EducationLevel.NONE;

    @ManyToOne
    private Occupation occupation;

    private boolean isAlive = true;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    private List<Relationship> relationships = new ArrayList<>();

    public Actor(String firstName, String lastName, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public Actor(String firstName, String lastName, int ageInMonths, Gender gender,
                 SocialClass socialClass, EducationLevel educationLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ageInMonths = ageInMonths;
        this.gender = gender;
        this.socialClass = socialClass;
        this.educationLevel = educationLevel;
    }

    public void addRelationship(Relationship relationship) {
        relationships.add(relationship);
        relationship.setActor(this);
    }

    public void removeRelationship(Relationship relationship) {
        relationships.remove(relationship);
        relationship.setActor(null);
    }
}
