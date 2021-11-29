package com.cinar.score.client;

import static java.util.Collections.emptySet;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AWSClient {

  public static final String HTTPS = "https";
  public static final String SEARCH_ALIAS = "search-alias";
  public static final String CLIENT = "client";

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final RestTemplate restTemplate;

  @Autowired
  public AWSClient(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Value("${client.amazon.host}")
  private String amazonHost;

  @Value("${client.amazon.path.autocomplete}")
  private String autocompletePath;

  public Set<String> getAutocompleteByKeyword(String keyword) throws JsonProcessingException {
    final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
        .scheme(HTTPS)
        .host(amazonHost)
        .path(autocompletePath)
        .queryParam(SEARCH_ALIAS, "aps")
        .queryParam(CLIENT, "amazon-search-ui")
        .queryParam("mkt", "1")
        .queryParam("q", keyword);

    final String body = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), String.class)
        .getBody();
    List<Object> response = objectMapper.readValue(body, List.class);

    return isEmpty(response) ? emptySet() : new HashSet<>(((List<String>) response.get(1)));
  }
}
