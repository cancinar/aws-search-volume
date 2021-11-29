package com.cinar.score.calculator;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ScoreCalculator implements Function<List<Set<String>>, Integer> {

  private String keyword;

  @Override
  public Integer apply(List<Set<String>> suggestions) {
    double total = 0D;
    double pointPerSuggestion = 100.0 / suggestions.size();
    for (Set<String> suggestion : suggestions) {
      for (String s : suggestion) {
        if (s.contains(keyword)) {
          double score = pointPerSuggestion / suggestion.size();
          total = total + score;
        }
      }
    }
    return (int) Math.round(total);
  }
}
