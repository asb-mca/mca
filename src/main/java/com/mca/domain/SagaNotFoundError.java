package com.mca.domain;

import com.mca.domain.base.NotFoundError;

public class SagaNotFoundError extends NotFoundError {
    public SagaNotFoundError(Integer videogameId) {
        super(String.format("No saga related to videogame %d was found", videogameId));
    }
}
