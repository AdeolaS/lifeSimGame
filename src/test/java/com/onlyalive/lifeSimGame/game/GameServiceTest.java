package com.onlyalive.lifeSimGame.game;

import com.onlyalive.lifeSimGame.actor.Actor;
import com.onlyalive.lifeSimGame.actor.ActorRepository;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.actor.properties.name.FirstName;
import com.onlyalive.lifeSimGame.actor.properties.name.FirstNameRepository;
import com.onlyalive.lifeSimGame.actor.properties.name.LastNameRepository;
import com.onlyalive.lifeSimGame.relationship.RelationshipService;
import com.onlyalive.lifeSimGame.relationship.RelationshipStatus;
import com.onlyalive.lifeSimGame.simulation.SimulationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private FirstNameRepository firstNameRepository;

    @Mock
    private LastNameRepository lastNameRepository;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private SimulationService simulationService;

    @Mock
    private RelationshipService relationshipService;

    @InjectMocks
    private GameService gameService;


    @Test
    void createGame_shouldCreateGameWithSuppliedPlayerDetails() {

        when(gameRepository.save(any(Game.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Game result = gameService.createGame(
                "Michael",
                "Opoku",
                Gender.MALE
        );

        assertNotNull(result);
        assertNotNull(result.getPlayer());

        assertEquals(
                "Michael",
                result.getPlayer().getFirstName()
        );

        assertEquals(
                "Opoku",
                result.getPlayer().getLastName()
        );

        assertEquals(
                Gender.MALE,
                result.getPlayer().getGender()
        );

        assertEquals(
                0,
                result.getPlayer().getAgeInMonths()
        );

        assertTrue(result.getPlayer().isAlive());

        assertEquals(
                LocalDate.now(),
                result.getCurrentDateInGame()
        );

        verify(actorRepository, times(3))
                .save(any(Actor.class));

        verify(gameRepository)
                .save(any(Game.class));

        verify(relationshipService)
                .generateParentRelationship(
                        any(Actor.class),
                        any(Actor.class)
                );

        verify(relationshipService, times(2))
                .createRelationship(
                        any(Actor.class),
                        any(Actor.class),
                        any(RelationshipStatus.class),
                        anyInt()
                );
    }


    @Test
    void createGame_shouldGenerateLastNameWhenNotProvided() {

        FirstName firstName = mock(FirstName.class);

        when(firstNameRepository.findFemaleName())
                .thenReturn(firstName);

        when(firstNameRepository.findMaleName())
                .thenReturn(firstName);

        when(firstNameRepository.findRandomName())
                .thenReturn(firstName);

        when(firstName.isFemaleName())
                .thenReturn(true);

        when(firstName.isMaleName())
                .thenReturn(false);

        when(firstName.getName())
                .thenReturn("Sarah");

        when(lastNameRepository.findRandomName().getName())
                .thenReturn("Smith");

        when(gameRepository.save(any(Game.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Game result = gameService.createGame(
                null,
                null,
                Gender.FEMALE
        );

        assertNotNull(result);

        Actor player = result.getPlayer();

        assertEquals(
                "Sarah",
                player.getFirstName()
        );

        assertEquals(
                "Smith",
                player.getLastName()
        );

        assertEquals(
                Gender.FEMALE,
                player.getGender()
        );

        verify(firstNameRepository)
                .findRandomName();

        verify(lastNameRepository)
                .findRandomName();
    }


    @Test
    void createGame_shouldGenerateParents() {

        FirstName playerName = mock(FirstName.class);
        FirstName motherName = mock(FirstName.class);
        FirstName fatherName = mock(FirstName.class);

        when(firstNameRepository.findRandomName())
                .thenReturn(playerName);

        when(firstNameRepository.findFemaleName())
                .thenReturn(motherName);

        when(firstNameRepository.findMaleName())
                .thenReturn(fatherName);

        when(playerName.getName())
                .thenReturn("Michael");

        when(playerName.isMaleName())
                .thenReturn(true);

        when(playerName.isFemaleName())
                .thenReturn(false);

        when(motherName.getName())
                .thenReturn("Sarah");

        when(fatherName.getName())
                .thenReturn("David");

        when(lastNameRepository.findRandomName().getName())
                .thenReturn("Smith");

        when(gameRepository.save(any(Game.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Game game = gameService.createGame(
                null,
                null,
                null
        );

        assertNotNull(game);

        Actor player = game.getPlayer();

        assertEquals(
                "Michael",
                player.getFirstName()
        );

        verify(firstNameRepository)
                .findFemaleName();

        verify(firstNameRepository)
                .findMaleName();

        verify(actorRepository, times(3))
                .save(any(Actor.class));
    }


    // ---------------------------------------------------------
    // ageUp()
    // ---------------------------------------------------------

    @Test
    void ageUp_shouldAdvanceSimulationAndSaveGame() {

        Long gameId = 1L;

        Game game = mock(Game.class);

        when(gameRepository.findById(gameId))
                .thenReturn(Optional.of(game));

        when(gameRepository.save(game))
                .thenReturn(game);

        Game result = gameService.ageUp(gameId);

        assertSame(game, result);

        verify(gameRepository)
                .findById(gameId);

        verify(simulationService)
                .advanceMonth(game);

        verify(gameRepository)
                .save(game);
    }


    @Test
    void ageUp_shouldThrowExceptionWhenGameDoesNotExist() {

        Long gameId = 999L;

        when(gameRepository.findById(gameId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> gameService.ageUp(gameId)
        );

        assertEquals(
                "Game not found",
                exception.getMessage()
        );

        verify(gameRepository)
                .findById(gameId);

        verifyNoInteractions(simulationService);

        verify(gameRepository, never())
                .save(any(Game.class));
    }


    // ---------------------------------------------------------
    // deleteAllGamesAndActors()
    // ---------------------------------------------------------

    @Test
    void deleteAllGamesAndActors_shouldDeleteActorsAndGames() {

        gameService.deleteAllGamesAndActors();

        verify(actorRepository)
                .deleteAll();

        verify(gameRepository)
                .deleteAll();
    }


    // ---------------------------------------------------------
    // getAllCharacters()
    // ---------------------------------------------------------

    @Test
    void getAllCharacters_shouldReturnAllActors() {

        Actor actor1 = new Actor(
                "Michael",
                "Smith",
                Gender.MALE
        );

        Actor actor2 = new Actor(
                "Sarah",
                "Smith",
                Gender.FEMALE
        );

        List<Actor> actors = List.of(
                actor1,
                actor2
        );

        when(actorRepository.findAll())
                .thenReturn(actors);

        List<Actor> result =
                gameService.getAllCharacters();

        assertEquals(
                2,
                result.size()
        );

        assertEquals(
                actors,
                result
        );

        verify(actorRepository)
                .findAll();
    }


    @Test
    void getAllCharacters_whenNoActors_shouldReturnEmptyList() {

        when(actorRepository.findAll())
                .thenReturn(List.of());

        List<Actor> result =
                gameService.getAllCharacters();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(actorRepository)
                .findAll();
    }
}
