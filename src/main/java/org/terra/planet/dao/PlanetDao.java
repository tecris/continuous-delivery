package org.terra.planet.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.terra.planet.model.Planet;

@Stateless
public class PlanetDao {

	@PersistenceContext(unitName = "pl-persistence-unit")
    private EntityManager em;

    public void create(Planet Planet) {
        this.em.persist(Planet);
    }

    public Planet findById(Long pid) {
        return this.em.find(Planet.class, pid);
    }

    public void update(Planet Planet) {
        this.em.merge(Planet);
    }

    public void delete(Long id) {
        Planet a = this.findById(id);
        if (a != null) {
            this.em.remove(a);
        }
    }

    public List<Planet> getAll() {

        CriteriaQuery<Planet> criteria = this.em.getCriteriaBuilder().createQuery(Planet.class);
        return this.em.createQuery(criteria.select(criteria.from(Planet.class))).getResultList();
    }

    public List<Planet> listAll(Integer startPosition, Integer maxResult) {
        CriteriaQuery<Planet> criteria = this.em.getCriteriaBuilder().createQuery(Planet.class);
        TypedQuery<Planet> findAllQuery = this.em.createQuery(criteria.select(criteria.from(Planet.class)));

        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        final List<Planet> results = findAllQuery.getResultList();
        return results;
    }
}
