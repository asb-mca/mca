package com.mca.controller;

import org.springframework.web.bind.annotation.RestController;

import com.mca.query.FindRelatedSagas;

import lombok.AllArgsConstructor;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@AllArgsConstructor
public class SagaController {
  private final FindRelatedSagas findRelatedSagas;

  @GetMapping("/game-saga/{sagaId}/related-sagas")
  public Collection<String> getVideoGamesByGameSaga(@PathVariable("sagaId") Integer sagaId) {
    return findRelatedSagas.bySaga(sagaId);
  }
  

}
