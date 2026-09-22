package com.onlyalive.lifeSimGame.actor.properties.occupation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OccupationRepository extends JpaRepository<Occupation, Long> {
    @Query (value = "SELECT * FROM occupations ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    Occupation findRandomOccupation();

    @Query (value = "SELECT * FROM occupations WHERE job_title = 'Unemployed'",
            nativeQuery = true)
    Occupation returnUnemployed();
}
