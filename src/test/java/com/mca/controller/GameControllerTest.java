package com.mca.controller;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mca.query.FindVideoGames;
import com.mca.query.VideoGameView;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {
    @Autowired
	private MockMvc mockMvc;
	
    @MockBean
    FindVideoGames findVideoGames;

    @Test
    public void getVideoGamesByGameSaga_ReturnsEmptySet() throws Exception{
        // Arrange
        when(findVideoGames.byGameSaga(1)).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc
        .perform(get("/game/{gameId}/saga", 1))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void getVideoGamesByGameSaga_ReturnsThreeElements() throws Exception {
        // Arrange
        final LocalDate now = LocalDate.now();
        final Date dNow = Date.valueOf(now);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        final List<VideoGameView> videoGames = new ArrayList<>();
        videoGames.add(VideoGameView.builder().id(1).title("title1").releaseDate(dNow).genre("shmup").build());
        videoGames.add(VideoGameView.builder().id(3).title("title3").releaseDate(dNow).genre("rpg").build());
        videoGames.add(VideoGameView.builder().id(2).title("title2").releaseDate(dNow).genre("puzzle").build());

        when(findVideoGames.byGameSaga(2)).thenReturn(videoGames);

        // Act & Assert
        mockMvc.perform(get("/game/2/saga"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$.length()").value(3))
               .andExpect(jsonPath("$.[0].id").value("1"))
               .andExpect(jsonPath("$.[0].title").value("title1"))
               .andExpect(jsonPath("$.[0].genre").value("shmup"))
               .andExpect(jsonPath("$.[0].releaseDate").value(sdf.format(dNow)))
               .andExpect(jsonPath("$.[1].id").value("3"))
               .andExpect(jsonPath("$.[2].id").value("2"));
    }
}
