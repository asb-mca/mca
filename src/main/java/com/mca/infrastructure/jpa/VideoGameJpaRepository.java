package com.mca.infrastructure.jpa;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mca.domain.VideoGame;
import com.mca.domain.VideoGameNotFoundError;
import com.mca.domain.VideoGameRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class VideoGameJpaRepository implements VideoGameRepository{
    private final VideoGameSpringJpaRepository repository;


    @Override
    public VideoGame findById(Integer id) {
        Optional<VideoGame> maybeVideoGame = this.repository.findById(id);

        if (!maybeVideoGame.isPresent()) {
            throw new VideoGameNotFoundError(id);
        }

        return maybeVideoGame.get();
    }

    @Override
    public void save(VideoGame videoGame) {
        this.repository.save(videoGame);
    }
    
}
