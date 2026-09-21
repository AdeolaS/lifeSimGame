package com.onlyalive.lifeSimGame.actor.properties.name;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FirstNameRepository extends JpaRepository<FirstName, Long> {
    @Query (value = "SELECT * FROM first_names ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    FirstName findRandomName();

    @Query (value = "SELECT * FROM first_names WHERE is_female_name=true ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    FirstName findFemaleName();

    @Query (value = "SELECT * FROM first_names WHERE is_male_name=true ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    FirstName findMaleName();
}
