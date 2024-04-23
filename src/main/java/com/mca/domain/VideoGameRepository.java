package com.mca.domain;

public interface VideoGameRepository {
    public VideoGame findById(Integer id);
    public void save(VideoGame videoGame);
}
