package info.eurisko.core.services;

import info.eurisko.core.domain.Newsletter;
import info.eurisko.core.events.newsletters.*;
import info.eurisko.core.repository.NewslettersRepository;

import java.util.*;

public class NewsletterEventHandler implements NewsletterService {

	private final NewslettersRepository newslettersRepository;

	public NewsletterEventHandler(final NewslettersRepository newslettersRepository) {
		this.newslettersRepository = newslettersRepository;
	}

	@Override
	public NewsletterCreatedEvent createNewsletter(CreateNewsletterEvent createNewsletterEvent) {
		Newsletter newsletter = Newsletter.fromNewsletterDetails(createNewsletterEvent.getDetails());

		newsletter = newslettersRepository.persist(newsletter);

		return new NewsletterCreatedEvent(newsletter.getKey(), newsletter.toNewsletterDetails());
	}

	@Override
	public AllNewslettersEvent requestAllNewsletters(RequestAllNewslettersEvent requestAllCurrentNewslettersEvent) {
		List<NewsletterDetails> generatedDetails = new ArrayList<NewsletterDetails>();
		for (Newsletter newsletter : newslettersRepository.findAll()) {
			generatedDetails.add(newsletter.toNewsletterDetails());
		}
		return new AllNewslettersEvent(generatedDetails);
	}

	@Override
	public NewsletterDetailsEvent requestNewsletterDetails(RequestNewsletterDetailsEvent requestNewsletterDetailsEvent) {

		Newsletter newsletter = newslettersRepository.find(requestNewsletterDetailsEvent.getKey());

		if (newsletter == null) {
			return NewsletterDetailsEvent.notFound(requestNewsletterDetailsEvent.getKey());
		}

		return new NewsletterDetailsEvent(requestNewsletterDetailsEvent.getKey(), newsletter.toNewsletterDetails());
	}
}
