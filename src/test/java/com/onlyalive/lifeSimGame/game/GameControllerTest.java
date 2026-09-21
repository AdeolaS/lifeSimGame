package com.onlyalive.lifeSimGame.game;

import com.onlyalive.lifeSimGame.actor.dto.ActorFullDto;
import com.onlyalive.lifeSimGame.actor.properties.Gender;
import com.onlyalive.lifeSimGame.game.dto.GameDto;
import com.onlyalive.lifeSimGame.game.dto.GameMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;
    @Autowired
    private GameMapper gameMapper;

    @Test
    void createGame_shouldReturn200() throws Exception {

        Game game = new Game();

        Long gameId = 1L;
        LocalDate date = LocalDate.now();
        ActorFullDto actorFullDto =
                new ActorFullDto(anyLong(), anyString(), anyString(), anyInt(), any(), anyBoolean(), anyList());

        GameDto gameDto = new GameDto(gameId, date, actorFullDto);

        when(gameService.createGame("Joanna", "Smithings", Gender.FEMALE))
                .thenReturn(game);

        when(gameMapper.toDto(game))
                .thenReturn(gameDto);

        mockMvc.perform(
                post("/game/new-game")
                        .param("firstName", "Joanna")
                        .param("lastName", "Smithings")
                        .param("gender", "FEMALE"))
            .andExpect(status().isOk());

        verify(gameService).createGame("Joanna", "Smithings", Gender.FEMALE);
    }

    @Test
    void createGame_shouldAllowMissingParameters() throws Exception {

        Game game = new Game();

        when(gameService.createGame(null, null, null))
                .thenReturn(game);

        mockMvc.perform(
                        post("/game/new-game"))
            .andExpect(status().isOk());

        verify(gameService).createGame(null, null, null);
    }

    @Test
    void advanceMonth_shouldReturn200() throws Exception {

        Long gameId = 1L;
        Game game = new Game();

        when(gameService.ageUp(gameId)).thenReturn(game);

        mockMvc.perform(
                        put("/game/{gameId}/age-up", gameId))
                .andExpect(status().isOk());

        verify(gameService).ageUp(gameId);
    }
}