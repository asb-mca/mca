package com.mca.infrastructure.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mca.query.VideoGameView;


@Repository
public interface VideoGameViewSpringJpaRepository extends JpaRepository<VideoGameView, Integer>{
    public List<VideoGameView> findAllBySagaId(Integer sagaId);
}
