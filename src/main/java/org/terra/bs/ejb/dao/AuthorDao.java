package org.terra.bs.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.terra.bs.entities.Author;

@Stateless
public class AuthorDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Author author) {
        this.em.persist(author);
    }

    public Author findById(Long pid) {
        return this.em.find(Author.class, pid);
    }

    public void update(Author author) {
        this.em.merge(author);
    }

    public void delete(Long id) {
        Author a = this.findById(id);
        if (a != null) {
            this.em.remove(a);
        }
    }

    public List<Author> getAll() {

        CriteriaQuery<Author> criteria = this.em.getCriteriaBuilder().createQuery(Author.class);
        return this.em.createQuery(criteria.select(criteria.from(Author.class))).getResultList();
    }

    public List<Author> listAll(Integer startPosition, Integer maxResult) {
        CriteriaQuery<Author> criteria = this.em.getCriteriaBuilder().createQuery(Author.class);
        TypedQuery<Author> findAllQuery = this.em.createQuery(criteria.select(criteria.from(Author.class)));

        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }
}
