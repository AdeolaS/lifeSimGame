package com.onlyalive.lifeSimGame.actor.properties.name;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LastNameRepository extends JpaRepository<LastName, Long> {
    @Query (value = "SELECT * FROM last_names ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    LastName findRandomName();
}
