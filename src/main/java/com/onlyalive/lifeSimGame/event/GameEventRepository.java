package com.onlyalive.lifeSimGame.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameEventRepository extends JpaRepository<GameEvent, Long> {
}
