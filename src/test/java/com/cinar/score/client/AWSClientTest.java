package com.cinar.score.client;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

@RunWith(SpringRunner.class)
@RestClientTest(AWSClient.class)
class AWSClientTest {

  @Autowired
  private AWSClient awsClient;


  @Autowired
  private MockRestServiceServer mockRestServiceServer;


  @Test
  public void getAutocompleteByKeyword_whenQueryIsIphone_thenReturn() throws JsonProcessingException {

    String json = "[\"iphone\",[\"iphone 13 pro max case\",\"iphone 13 case\",\"iphone 11 case\",\"iphone charger\",\"iphone 13 pro case\",\"iphone 12 pro max case\",\"iphone 12 case\",\"iphone\",\"iphone 11\",\"iphone 13\"],[{},{},{},{},{},{},{},{},{},{}],[],\"3R4PVLIHPGXHZ\"]";

    this.mockRestServiceServer
        .expect(requestTo(
            "https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q=iphone"))
        .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

    final Set<String> autocompleteByKeyword = awsClient.getAutocompleteByKeyword("iphone");

    assertTrue(!autocompleteByKeyword.isEmpty());
  }


}
