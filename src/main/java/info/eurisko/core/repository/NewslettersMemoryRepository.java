package info.eurisko.core.repository;

import info.eurisko.core.domain.Newsletter;

import java.util.*;

public class NewslettersMemoryRepository implements NewslettersRepository {

	private static final Map<UUID, Newsletter> emptyNewsletterList = new HashMap<UUID, Newsletter>();

	private Map<UUID, Newsletter> newsletters;

	public NewslettersMemoryRepository() {
		this.newsletters = Collections.unmodifiableMap(emptyNewsletterList);
	}

	@Override
	public synchronized Newsletter persist(Newsletter newsletter) {
		newsletter.setKey(UUID.randomUUID());

		Map<UUID, Newsletter> modifiableNewsletters = new HashMap<UUID, Newsletter>(newsletters);
		modifiableNewsletters.put(newsletter.getKey(), newsletter);
		this.newsletters = Collections.unmodifiableMap(modifiableNewsletters);

		return newsletter;
	}

	@Override
	public Newsletter find(UUID key) {
		return newsletters.get(key);
	}

	@Override
	public Collection<Newsletter> findAll() {
		return Collections.unmodifiableList(new ArrayList<Newsletter>(newsletters.values()));
	}
}
