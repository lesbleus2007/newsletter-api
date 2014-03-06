package info.eurisko.rest.domain;

import info.eurisko.core.events.newsletters.NewsletterDetails;
import info.eurisko.rest.controller.fixture.RestDataFixture;
import info.eurisko.rest.domain.Newsletter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;

public class NewsletterTests {

  // {!begin supportHateoas}
  @Before
  public void setup() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }
  // {!end supportHateoas}

  @Test
  public void thatNewsletterCanConvertToNewsletterDetails() {
    Newsletter newsletter = RestDataFixture.standardNewsletter();

    NewsletterDetails details = newsletter.toNewsletterDetails();

    assertEquals(newsletter.getKey(), details.getKey());
    assertEquals(newsletter.getDateTimeOfSubmission(), details.getDateTimeOfSubmission());
  }

  @Test
  public void thatNewsletterCanConvertFromNewsletterDetails() {
    NewsletterDetails details = RestDataFixture.standardNewsletterDetails();

    Newsletter newsletter = Newsletter.fromNewsletterDetails(details);

    assertEquals(newsletter.getKey(), details.getKey());
    assertEquals(newsletter.getDateTimeOfSubmission(), details.getDateTimeOfSubmission());
  }
}
