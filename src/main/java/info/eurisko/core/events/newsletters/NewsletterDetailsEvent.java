package info.eurisko.core.events.newsletters;

import info.eurisko.core.events.ReadEvent;

import java.util.UUID;

public class NewsletterDetailsEvent extends ReadEvent {
	private UUID key;
	private NewsletterDetails newsletterDetails;

	private NewsletterDetailsEvent(UUID key) {
		this.key = key;
	}

	public NewsletterDetailsEvent(UUID key, NewsletterDetails newsletterDetails) {
		this.key = key;
		this.newsletterDetails = newsletterDetails;
	}

	public UUID getKey() {
		return key;
	}

	public NewsletterDetails getNewsletterDetails() {
		return newsletterDetails;
	}

	public static NewsletterDetailsEvent notFound(UUID key) {
		NewsletterDetailsEvent ev = new NewsletterDetailsEvent(key);
		ev.entityFound = false;
		return ev;
	}
}
