package com.mca.domain;

import com.mca.domain.base.NotFoundError;

import lombok.Getter;

public class VideoGameNotFoundError extends NotFoundError {
    @Getter
    private final Integer videoGameId;

    public VideoGameNotFoundError(Integer id) {
        super(String.format("Video game %d was not found", id));
        this.videoGameId = id;
    }
}
