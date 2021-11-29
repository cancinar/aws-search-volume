package com.cinar.score.service;


import static org.springframework.util.ObjectUtils.isEmpty;

import com.cinar.score.calculator.ScoreCalculator;
import com.cinar.score.client.AWSClient;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@AllArgsConstructor
class ScoreComponentImpl implements ScoreComponent {

  private final AWSClient awsClient;
  private final ExecutorService executorService = Executors.newFixedThreadPool(10);
  private final Function<Future<Set<String>>, Set<String>> getFromFuture = (Future<Set<String>> future) -> {
    try {
      return future.get();
    } catch (InterruptedException | ExecutionException e) {
      log.error("Exception occurred during future get.", e);
    }
    return null;
  };

  @Override
  public int getScore(String keyword) throws InterruptedException {
    keyword = StringUtils.trimAllWhitespace(keyword);

    if (isEmpty(keyword)) {
      return 0;
    }

    ScoreCalculator scoreCalculator = new ScoreCalculator(keyword);

    return scoreCalculator.apply(getSuggestions(keyword));
  }

  private List<Set<String>> getSuggestions(String keyword) throws InterruptedException {
    final List<Callable<Set<String>>> callables = IntStream.rangeClosed(1, keyword.length())
        .mapToObj(i -> keyword.substring(0, i))
        .map(prefix -> (Callable<Set<String>>) () -> awsClient.getAutocompleteByKeyword(prefix))
        .collect(Collectors.toList());

    return executorService.invokeAll(callables, 10, TimeUnit.SECONDS)
        .stream()
        .filter(it -> !it.isCancelled())
        .map(getFromFuture)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}