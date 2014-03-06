package info.eurisko.rest.functional;

import org.junit.Test;
import org.springframework.http.*;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import info.eurisko.rest.controller.fixture.RestDataFixture;
import info.eurisko.rest.domain.Newsletter;

import java.util.Arrays;

import static junit.framework.TestCase.*;

public class NewsletterTests {

  @Test
  public void thatNewslettersCanBeAddedAndQueried() {

    ResponseEntity<Newsletter> entity = createNewsletter();

    String path = entity.getHeaders().getLocation().getPath();

    assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    assertTrue(path.startsWith("/aggregators/newsletters/"));
    Newsletter newsletter = entity.getBody();

    System.out.println ("The Newsletter ID is " + newsletter.getKey());
    System.out.println ("The Location is " + entity.getHeaders().getLocation());
  }

  @Test
  public void thatNewslettersCannotBeAddedAndQueriedWithBadUser() {

    HttpEntity<String> requestEntity = new HttpEntity<String>(
        RestDataFixture.standardNewsletterJSON(),
        getHeaders("letsnosh" + ":" + "BADPASSWORD"));

    RestTemplate template = new RestTemplate();
    try {
      ResponseEntity<Newsletter> entity = template.postForEntity(
          "http://localhost:8080/aggregators/newsletters",
          requestEntity, Newsletter.class);

      fail("Request Passed incorrectly with status " + entity.getStatusCode());
    } catch (HttpClientErrorException ex) {
      assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
    }
  }

  // {!begin thatNewslettersHaveCorrectHateoasLinks}
  @Test
  public void thatNewslettersHaveCorrectHateoasLinks() {

    ResponseEntity<Newsletter> entity = createNewsletter();

    Newsletter newsletter = entity.getBody();

    assertEquals(entity.getHeaders().getLocation().toString(), newsletter.getLink("self").getHref());
  }
  // {!end thatNewslettersHaveCorrectHateoasLinks}

  private ResponseEntity<Newsletter> createNewsletter() {
    HttpEntity<String> requestEntity = new HttpEntity<String>(
        RestDataFixture.standardNewsletterJSON(),getHeaders("letsnosh" + ":" + "noshing"));

    RestTemplate template = new RestTemplate();
    return template.postForEntity(
        "http://localhost:8080/aggregators/newsletters",
        requestEntity, Newsletter.class);
  }

  static HttpHeaders getHeaders(String auth) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
    headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

    return headers;
  }
}


