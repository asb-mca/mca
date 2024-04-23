package com.mca.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Test;

import com.mca.domain.VideoGame;
import com.mca.domain.VideoGameRepository;

public class ProcessStockChangeTest {

    @Test
    public void execute_shouldPersistVideoGame() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        VideoGameRepository mockRepo = mock(VideoGameRepository.class);
        ProcessStockChange psc = new ProcessStockChange(mockRepo);

        ProcessStockChange.Input input = ProcessStockChange.Input.builder()
                .date(now)
                .videoGameId(1)
                .isAvailable(true)
                .build();

        VideoGame vg = VideoGame.builder().id(input.getVideoGameId()).build();
        
        when(mockRepo.findById(input.getVideoGameId())).thenReturn(vg);

        // Act
        psc.execute(input);

        // Assert
        verify(mockRepo).findById(input.getVideoGameId());
        verify(mockRepo).save(vg);

    }
}
