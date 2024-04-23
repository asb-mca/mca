package com.mca.infrastructure.jpa;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.mca.domain.VideoGame;
import com.mca.domain.VideoGameNotFoundError;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

public class VideoGameJpaRepositoryTest {
    

    @Test
    public void findById_ThrowsException_WhenVideoGameNotFound() {
        // Arrange
        final VideoGameSpringJpaRepository repositoryMock = mock(VideoGameSpringJpaRepository.class);
        final VideoGameJpaRepository jpaRepository = new VideoGameJpaRepository(repositoryMock);

        Integer videoGameId = 1;
        when(repositoryMock.findById(videoGameId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VideoGameNotFoundError.class, () -> {
            jpaRepository.findById(videoGameId);
        });
    }

    @Test
    public void findById_ReturnsVideoGame_WhenFound() {
        // Arrange
        final VideoGameSpringJpaRepository repositoryMock = mock(VideoGameSpringJpaRepository.class);
        final VideoGameJpaRepository jpaRepository = new VideoGameJpaRepository(repositoryMock);

        Integer videoGameId = 1;
        VideoGame expectedVideoGame = VideoGame.builder().id(videoGameId).build();
        when(repositoryMock.findById(videoGameId)).thenReturn(Optional.of(expectedVideoGame));

        // Act
        VideoGame actualVideoGame = jpaRepository.findById(videoGameId);

        // Assert
        assertNotNull(actualVideoGame);
        assertEquals(expectedVideoGame, actualVideoGame);
    }

    @Test
    public void save_CallsRepositorySaveMethod() {
        // Arrange
        final VideoGameSpringJpaRepository repositoryMock = mock(VideoGameSpringJpaRepository.class);
        final VideoGameJpaRepository jpaRepository = new VideoGameJpaRepository(repositoryMock);
        
        VideoGame videoGame = new VideoGame();

        // Act
        jpaRepository.save(videoGame);

        // Assert
        verify(repositoryMock).save(videoGame);
    }
}
