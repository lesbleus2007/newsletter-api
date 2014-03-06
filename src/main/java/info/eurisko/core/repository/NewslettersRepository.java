package info.eurisko.core.repository;

import info.eurisko.core.domain.Newsletter;

import java.util.Collection;
import java.util.UUID;

//TODO, make this event based again, with persistence integration events.
public interface NewslettersRepository {

	Newsletter persist(Newsletter newsletter);

	Newsletter find(UUID key);

	Collection<Newsletter> findAll();
}
