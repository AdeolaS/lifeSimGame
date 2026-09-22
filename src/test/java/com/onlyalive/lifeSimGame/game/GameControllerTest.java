package com.onlyalive.lifeSimGame.game;

import com.onlyalive.lifeSimGame.actor.Actor;
import com.onlyalive.lifeSimGame.actor.dto.ActorMapper;
import com.onlyalive.lifeSimGame.actor.dto.ActorFullDto;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.game.dto.GameDto;
import com.onlyalive.lifeSimGame.game.dto.GameMapper;
import com.onlyalive.lifeSimGame.relationship.RelationshipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    @MockitoBean
    private GameMapper gameMapper;

    @MockitoBean
    private ActorMapper actorMapper;

    @MockitoBean
    private RelationshipService relationshipService;

    @Test
    void createGame_shouldCreateGameSuccessfully() throws Exception {

        Game game = new Game();
        GameDto gameDto = mock(GameDto.class);

        when(gameService.createGame(
                "Michael",
                "Opoku",
                Gender.MALE
        )).thenReturn(game);

        when(gameMapper.toDto(game)).thenReturn(gameDto);

        mockMvc.perform(
                        post("/game/new-game")
                                .param("firstName", "Michael")
                                .param("lastName", "Opoku")
                                .param("gender", "MALE")
                ).andExpect(status().isOk());

        verify(relationshipService).deleteAllRelationships();

        verify(gameService).deleteAllGamesAndActors();

        verify(gameService).createGame(
                        "Michael",
                        "Opoku",
                        Gender.MALE);

        verify(gameMapper).toDto(game);
    }

    @Test
    void createGame_shouldAllowMissingParameters() throws Exception {

        Game game = new Game();
        GameDto gameDto = mock(GameDto.class);

        when(gameService.createGame(null, null, null)).thenReturn(game);

        when(gameMapper.toDto(game)).thenReturn(gameDto);

        mockMvc.perform(post("/game/new-game"))
                .andExpect(status().isOk());

        verify(relationshipService).deleteAllRelationships();

        verify(gameService).deleteAllGamesAndActors();

        verify(gameService).createGame(
                        null,
                        null,
                        null);

        verify(gameMapper).toDto(game);
    }

    @Test
    void advanceMonth_shouldAgeGameSuccessfully() throws Exception {

        Long gameId = 1L;

        Game game = new Game();
        GameDto gameDto = mock(GameDto.class);

        when(gameService.ageUp(gameId)).thenReturn(game);

        when(gameMapper.toDto(game)).thenReturn(gameDto);

        mockMvc.perform(put("/game/{gameId}/age-up", gameId))
                .andExpect(status().isOk());

        verify(gameService).ageUp(gameId);

        verify(gameMapper).toDto(game);
    }

    @Test
    void getAllCharacters_shouldReturnAllActors() throws Exception {

        Actor actor1 = new Actor();
        Actor actor2 = new Actor();

        ActorFullDto dto1 = mock(ActorFullDto.class);
        ActorFullDto dto2 = mock(ActorFullDto.class);

        when(gameService.getAllCharacters()).thenReturn(List.of(actor1, actor2));

        when(actorMapper.toDto(actor1)).thenReturn(dto1);

        when(actorMapper.toDto(actor2)).thenReturn(dto2);

        mockMvc.perform(get("/game/all-actors"))
                .andExpect(status().isOk());

        verify(gameService).getAllCharacters();

        verify(actorMapper).toDto(actor1);

        verify(actorMapper).toDto(actor2);
    }

    @Test
    void getAllCharacters_whenNoActors_shouldReturnEmptyList() throws Exception {

        when(gameService.getAllCharacters()).thenReturn(List.of());

        mockMvc.perform(get("/game/all-actors"))
                .andExpect(status().isOk());

        verify(gameService).getAllCharacters();

        verifyNoInteractions(actorMapper);
    }
}