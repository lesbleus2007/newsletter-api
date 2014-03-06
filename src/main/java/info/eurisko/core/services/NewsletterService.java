package info.eurisko.core.services;

import info.eurisko.core.events.newsletters.*;

/**
 * This is an event driven service used to interact with the core domain.
 * All methods are guaranteed to return something, null will never be returned.
 */
public interface NewsletterService {

	public AllNewslettersEvent requestAllNewsletters(RequestAllNewslettersEvent requestAllCurrentNewslettersEvent);

	public NewsletterDetailsEvent requestNewsletterDetails(RequestNewsletterDetailsEvent requestNewsletterDetailsEvent);

	public NewsletterCreatedEvent createNewsletter(CreateNewsletterEvent event);
}
