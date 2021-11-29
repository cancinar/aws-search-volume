package com.cinar.score.service;


import static com.cinar.score.generator.RandomGenerator.randomKeyword;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cinar.score.client.AWSClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScoreComponentImplTest {

  private ScoreComponent scoreComponent;

  @Mock
  private AWSClient awsClient;

  @BeforeEach
  public void init() {
    scoreComponent = new ScoreComponentImpl(awsClient);
  }

  @Test
  public void getScore_whenAllMatches_thenReturnMax() throws InterruptedException, JsonProcessingException {
    String keyword = randomKeyword();

    when(awsClient.getAutocompleteByKeyword(any(String.class))).thenReturn(
        Set.of(
            keyword + "a",
            keyword + "b",
            keyword + "c",
            keyword + "d",
            keyword + "e",
            keyword + "f",
            keyword + "g",
            keyword + "h",
            keyword + "i",
            keyword + "z"));

    final int score = scoreComponent.getScore(keyword);

    assertEquals(100, score);

  }

  @Test
  public void getScore_whenNoneMatches_thenReturnZero() throws InterruptedException, JsonProcessingException {
    String keyword = randomKeyword();

    when(awsClient.getAutocompleteByKeyword(any(String.class)))
        .thenReturn(Collections.emptySet());

    final int score = scoreComponent.getScore(keyword);

    assertEquals(0, score);

  }

}