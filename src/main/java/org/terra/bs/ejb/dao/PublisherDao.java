package org.terra.bs.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.terra.bs.entities.Publisher;

@Stateless
public class PublisherDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Publisher publisher) {
        this.em.persist(publisher);
    }

    public Publisher findById(int pid) {
        return this.em.find(Publisher.class, pid);
    }

    public void update(Publisher publisher) {
        this.em.merge(publisher);
    }

    public void delete(int id) {
        Publisher a = this.findById(id);
        if (a != null) {
            this.em.remove(a);
        }
    }

    public List<Publisher> getAll() {

        CriteriaQuery<Publisher> criteria = this.em.getCriteriaBuilder().createQuery(Publisher.class);
        return this.em.createQuery(criteria.select(criteria.from(Publisher.class))).getResultList();
    }

    public List<Publisher> listAll(Integer startPosition, Integer maxResult) {
        CriteriaQuery<Publisher> criteria = this.em.getCriteriaBuilder().createQuery(Publisher.class);
        TypedQuery<Publisher> findAllQuery = this.em.createQuery(criteria.select(criteria.from(Publisher.class)));

        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        final List<Publisher> results = findAllQuery.getResultList();
        return results;
    }
}
