package com.onlyalive.lifeSimGame.actor.properties;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FirstNameRepository extends JpaRepository<FirstName, Long> {
    @Query (value = "SELECT * FROM first_names ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    FirstName findRandomName();
}
