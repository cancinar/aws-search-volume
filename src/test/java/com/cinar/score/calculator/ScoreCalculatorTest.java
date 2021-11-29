package com.cinar.score.calculator;

import static com.cinar.score.generator.RandomGenerator.randomKeyword;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ScoreCalculatorTest {

  @Test
  public void apply_whenAllIncludes_thenReturnMax() {
    String keyword = randomKeyword();
    final List<Set<String>> sets = List.of(
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
    final Integer apply = new ScoreCalculator(keyword).apply(sets);

    assertEquals(100, (int) apply);
  }

  @Test
  public void apply_whenNonIncludes_thenReturnZero() {
    String keyword = randomKeyword();
    final List<Set<String>> sets = List.of(
        Set.of());
    final Integer apply = new ScoreCalculator(keyword).apply(sets);

    assertEquals(0, (int) apply);
  }

}
