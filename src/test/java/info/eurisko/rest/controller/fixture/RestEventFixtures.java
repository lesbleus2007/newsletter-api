package info.eurisko.rest.controller.fixture;

import static info.eurisko.rest.controller.fixture.RestDataFixture.*;
import info.eurisko.core.events.newsletters.*;

import java.util.UUID;

public class RestEventFixtures {
  public static NewsletterDetailsEvent newsletterDetailsNotFound(UUID key) {
    return NewsletterDetailsEvent.notFound(key);
  }
  public static NewsletterDetailsEvent newsletterDetailsEvent(UUID key) {
    return new NewsletterDetailsEvent(key, customKeyNewsletterDetails(key));
  }
  public static NewsletterCreatedEvent newsletterCreated(UUID key) {
    return new NewsletterCreatedEvent(key, customKeyNewsletterDetails(key));
  }
}
