package com.cinar.score.generator;

import com.github.javafaker.Faker;

public class RandomGenerator {

  private final static Faker faker = new Faker();

  public static String randomKeyword() {
    return faker.lorem().characters(1, 10, true);
  }
}
