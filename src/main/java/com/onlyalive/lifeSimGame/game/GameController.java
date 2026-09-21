package com.onlyalive.lifeSimGame.game;

import com.onlyalive.lifeSimGame.actor.Actor;
import com.onlyalive.lifeSimGame.actor.dto.ActorFullDto;
import com.onlyalive.lifeSimGame.actor.dto.ActorMapper;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.game.dto.GameDto;
import com.onlyalive.lifeSimGame.game.dto.GameMapper;
import com.onlyalive.lifeSimGame.relationship.RelationshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@RestController 
@RequestMapping("/game")
@RequiredArgsConstructor 
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;
    private final ActorMapper actorMapper;
    private final RelationshipService relationshipService;

    @Transactional
    @PostMapping("/new-game")
    public ResponseEntity<GameDto> createGame(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Gender gender
    ) {
        relationshipService.deleteAllRelationships();
        gameService.deleteAllGamesAndActors();

        Game game = gameService.createGame(firstName, lastName, gender);
        return ResponseEntity.ok(gameMapper.toDto(game));
    }

    @PutMapping("/{gameId}/age-up")
    public ResponseEntity<GameDto> advanceMonth(@PathVariable Long gameId) {

        Game game = gameService.ageUp(gameId);
        return ResponseEntity.ok(gameMapper.toDto(game));
    }

    @GetMapping("/all-actors")
    public ResponseEntity<List<ActorFullDto>> getAllCharacters() {

        List<Actor> characters = gameService.getAllCharacters();
        List<ActorFullDto> characterDtos = new ArrayList<>();

        for (Actor character : characters) {
            characterDtos.add(actorMapper.toDto(character));
        }
        return ResponseEntity.ok(characterDtos);
    }
}
