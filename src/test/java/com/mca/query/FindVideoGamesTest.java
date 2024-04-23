package com.mca.query;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mca.domain.SagaNotFoundError;
import com.mca.domain.VideoGame;
import com.mca.domain.VideoGameNotFoundError;
import com.mca.domain.VideoGameSaga;
import com.mca.infrastructure.jpa.VideoGameSpringJpaRepository;
import com.mca.infrastructure.jpa.VideoGameViewSpringJpaRepository;

public class FindVideoGamesTest {
    @Test
    public void byGameSaga_ShouldReturnVideoGamesForGivenSaga() {
        // Arrange
        final VideoGameSpringJpaRepository videoGameRepositoryMock = mock(VideoGameSpringJpaRepository.class);
        final VideoGameViewSpringJpaRepository videoGameViewRepositoryMock = mock(VideoGameViewSpringJpaRepository.class);
        final FindVideoGames findVideoGames  = new FindVideoGames(videoGameRepositoryMock, videoGameViewRepositoryMock);

        VideoGame vg = VideoGame.builder()
            .id(1)
            .saga(VideoGameSaga.builder().id(100).build())
            .build();

        
        when(videoGameRepositoryMock.findById(vg.getId())).thenReturn(Optional.of(vg));

        List<VideoGameView> expectedVideoGames = new ArrayList<>();
        VideoGameView videoGameView1 = VideoGameView.builder().id(2).build();
        VideoGameView videoGameView2 = VideoGameView.builder().id(3).build();
        VideoGameView videoGameView3 = VideoGameView.builder().id(1).build();
        expectedVideoGames.add(videoGameView1);
        expectedVideoGames.add(videoGameView2);
        expectedVideoGames.add(videoGameView3);
        when(videoGameViewRepositoryMock.findAllBySagaId(vg.getSaga().getId())).thenReturn(expectedVideoGames);

        // Act
        List<VideoGameView> result = findVideoGames.byGameSaga(vg.getId());

        // Assert
        assertEquals(expectedVideoGames, result);
    }

    @Test
    public void byGameSaga_ShouldThrowVideoGameNotFoundError_WhenVideoGameNotFound() {
        // Arrange
        final VideoGameSpringJpaRepository videoGameRepositoryMock = mock(VideoGameSpringJpaRepository.class);
        final VideoGameViewSpringJpaRepository videoGameViewRepositoryMock = mock(VideoGameViewSpringJpaRepository.class);
        final FindVideoGames findVideoGames  = new FindVideoGames(videoGameRepositoryMock, videoGameViewRepositoryMock);
        
        Integer videogameId = 1;
        when(videoGameRepositoryMock.findById(videogameId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VideoGameNotFoundError.class, () -> {
            findVideoGames.byGameSaga(videogameId);
        });
    }

    @Test
    public void byGameSaga_ShouldThrowSagaNotFoundError_WhenSagaNotFound() {
        // Arrange
        final VideoGameSpringJpaRepository videoGameRepositoryMock = mock(VideoGameSpringJpaRepository.class);
        final VideoGameViewSpringJpaRepository videoGameViewRepositoryMock = mock(VideoGameViewSpringJpaRepository.class);
        final FindVideoGames findVideoGames  = new FindVideoGames(videoGameRepositoryMock, videoGameViewRepositoryMock);

        Integer videogameId = 1;
        VideoGame videoGame = new VideoGame();
        videoGame.setId(videogameId);
        when(videoGameRepositoryMock.findById(videogameId)).thenReturn(Optional.of(videoGame));

        // Act & Assert
        assertThrows(SagaNotFoundError.class, () -> {
            findVideoGames.byGameSaga(videogameId);
        });
    }
}
