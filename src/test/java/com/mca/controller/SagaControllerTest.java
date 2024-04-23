package com.mca.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.mca.query.FindRelatedSagas;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WebMvcTest(SagaController.class)
public class SagaControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
    @MockBean
    private FindRelatedSagas findRelatedSagas;

    @Test
    public void getRelatedSagas_ReturnsTwoElements() throws Exception {
        // Arrange
        Integer sagaId = 1;
        List<String> relatedSagas = new ArrayList<>();
        relatedSagas.add("Saga1");
        relatedSagas.add("Saga2");

        when(findRelatedSagas.bySaga(sagaId)).thenReturn(relatedSagas);

        // Act & Assert
        mockMvc.perform(get("/game-saga/1/related-sagas"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0]").value("Saga1"))
               .andExpect(jsonPath("$[1]").value("Saga2"));
    }

    @Test
    public void getRelatedSagas_ReturnsZeroElements() throws Exception {
        // Arrange
        Integer sagaId = 2;
        Set<String> relatedSagas = new HashSet<>();

        when(findRelatedSagas.bySaga(sagaId)).thenReturn(relatedSagas);

        // Act & Assert
        mockMvc.perform(get("/game-saga/2/related-sagas"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$.length()").value(0));
    }
}