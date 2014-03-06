package info.eurisko.core.repository;

import info.eurisko.core.domain.Newsletter;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class NewslettersPersistentRepository implements NewslettersRepository {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Newsletter find(final UUID id) {
		return em.find(Newsletter.class, id);
	}

	@Override
	public Newsletter persist(final Newsletter newsletter) {
		em.persist(newsletter);
		return newsletter;
	}

	@Override
	public Collection<Newsletter> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Newsletter> cq = cb.createQuery(Newsletter.class);
		Root<Newsletter> pet = cq.from(Newsletter.class);
		cq.select(pet);
		TypedQuery<Newsletter> q = em.createQuery(cq);
		return q.getResultList();
	}
}