package com.mca.controller;

import org.springframework.web.bind.annotation.RestController;

import com.mca.query.FindVideoGames;
import com.mca.query.VideoGameView;

import lombok.AllArgsConstructor;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@AllArgsConstructor
public class GameController {
  private FindVideoGames findVideoGames;

  @GetMapping("/game/{gameId}/saga")
  public Collection<VideoGameView> getVideoGamesByGameSaga(@PathVariable("gameId") String gameId) {
    return findVideoGames.byGameSaga(Integer.parseInt(gameId));
  }
}
