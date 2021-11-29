package com.cinar.score.view.controller;

import com.cinar.score.service.ScoreComponent;
import com.cinar.score.view.dto.ScoreDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/estimate")
public class ScoreController {

  private final ScoreComponent scoreComponent;

  @GetMapping
  public ResponseEntity<ScoreDto> getScore(@RequestParam("keyword") String keyword)
      throws InterruptedException {
    int score = scoreComponent.getScore(keyword);
    return ResponseEntity.ok(new ScoreDto(keyword, score));
  }
}
