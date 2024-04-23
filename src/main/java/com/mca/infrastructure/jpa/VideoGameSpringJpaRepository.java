package com.mca.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mca.domain.VideoGame;

public interface VideoGameSpringJpaRepository extends JpaRepository<VideoGame, Integer> {
    
}
