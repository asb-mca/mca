package com.mca.query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mca.domain.SagaNotFoundError;
import com.mca.domain.VideoGame;
import com.mca.domain.VideoGameNotFoundError;
import com.mca.infrastructure.jpa.VideoGameSpringJpaRepository;
import com.mca.infrastructure.jpa.VideoGameViewSpringJpaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindVideoGames {
    private final VideoGameSpringJpaRepository videoGameRepository;
    private final VideoGameViewSpringJpaRepository videoGameViewRepository;
    
    public List<VideoGameView> byGameSaga(Integer videogameId) {
        Optional<VideoGame> maybeVideoGame = this.videoGameRepository.findById(videogameId);

        if (!maybeVideoGame.isPresent()) {
            throw new VideoGameNotFoundError(videogameId);
        }

        if (Objects.isNull(maybeVideoGame.get().getSaga())) {
            throw new SagaNotFoundError(maybeVideoGame.get().getId());
        }

        return this.videoGameViewRepository.findAllBySagaId(maybeVideoGame.get().getSaga().getId());
    }
    
}
