package com.onlyalive.lifeSimGame.actor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor,Long> {

    @Query(value = "SELECT * FROM actors WHERE is_alive = true",
            nativeQuery = true)
    List<Actor> findLivingActors();
}
