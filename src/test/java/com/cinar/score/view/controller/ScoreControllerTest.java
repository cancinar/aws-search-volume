package com.cinar.score.view.controller;


import static com.cinar.score.generator.RandomGenerator.randomKeyword;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cinar.score.service.ScoreComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ScoreController.class)
class ScoreControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ScoreComponent scoreComponent;

  @Test
  void getScore_thenReturns200() throws Exception {
    mockMvc.perform(get("/estimate")
            .queryParam("keyword", randomKeyword())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}