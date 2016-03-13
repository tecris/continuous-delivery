package org.terra.bs.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.terra.bs.entities.Genre;

@Stateless
public class GenreDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Genre genre) {
        this.em.persist(genre);
    }

    public Genre findById(int pid) {
        return this.em.find(Genre.class, pid);
    }

    public void update(Genre genre) {
        this.em.merge(genre);
    }

    public void delete(int id) {
        Genre a = this.findById(id);
        if (a != null) {
            this.em.remove(a);
        }
    }

    public List<Genre> getAll() {

        CriteriaQuery<Genre> criteria = this.em.getCriteriaBuilder().createQuery(Genre.class);
        return this.em.createQuery(criteria.select(criteria.from(Genre.class))).getResultList();
    }

    public List<Genre> listAll(Integer startPosition, Integer maxResult) {
        CriteriaQuery<Genre> criteria = this.em.getCriteriaBuilder().createQuery(Genre.class);
        TypedQuery<Genre> findAllQuery = this.em.createQuery(criteria.select(criteria.from(Genre.class)));

        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }
}
