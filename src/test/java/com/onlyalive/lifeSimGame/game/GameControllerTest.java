package com.onlyalive.lifeSimGame.game;

import com.onlyalive.lifeSimGame.actor.properties.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    void createGame_shouldReturn200() throws Exception {

        Game game = new Game();

        when(gameService.createGame("Joanna", "Smithings", Gender.FEMALE))
                .thenReturn(game);

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