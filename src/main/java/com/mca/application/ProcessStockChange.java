package com.mca.application;

import java.time.LocalDateTime;

import com.mca.application.base.UseCase;
import com.mca.application.base.UseCaseInput;
import com.mca.domain.VideoGame;
import com.mca.domain.VideoGameRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
public class ProcessStockChange implements UseCase<ProcessStockChange.Input> {
    private final VideoGameRepository videoGameRepository;


    @Override
    public void execute(ProcessStockChange.Input input) {
        VideoGame videoGame = this.videoGameRepository.findById(input.videoGameId);

        videoGame.changeStock(input.isAvailable, input.date);

        this.videoGameRepository.save(videoGame);
    }

    @Data
    @Builder
    public static class Input implements UseCaseInput {
        LocalDateTime date;
        Integer videoGameId;
        boolean isAvailable;       
    }
}
