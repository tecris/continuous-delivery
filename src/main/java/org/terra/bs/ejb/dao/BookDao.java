package org.terra.bs.ejb.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.terra.bs.entities.Book;

@Stateful
public class BookDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Book book) {
        this.em.persist(book);
    }

    public Book findById(int pid) {
        // this.em.find(Book.class, pid); while simple, suffers from
        // org.hibernate.LazyInitializationException: could not initialize proxy
        // - no Session

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = cb.createQuery(Book.class);

        Root<Book> bookRoot = criteriaQuery.from(Book.class);
        bookRoot.fetch("author");
        bookRoot.fetch("genre");

        Predicate condition = cb.equal(bookRoot.get("bookId"), pid);
        criteriaQuery.where(condition);

        Query query = em.createQuery(criteriaQuery.select(bookRoot));
        return query.getResultList().isEmpty() ? null : (Book) query.getSingleResult();
    }

    public void update(Book book) {
        this.em.merge(book);
    }

    public void delete(int id) {
        Book a = this.findById(id);
        if (a != null) {
            this.em.remove(a);
        }
    }

    public List<Book> getAll() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);

        Root<Book> bookRoot = query.from(Book.class);
        bookRoot.fetch("author");
        bookRoot.fetch("genre");
        query.distinct(true);

        return em.createQuery(query.select(bookRoot)).getResultList();
    }

    public List<Book> listAll(Integer startPosition, Integer maxResult) {

        // TODO is this optimal ???
        List<Book> bookList = this.getAll();
        if (startPosition == null && maxResult == null) {
            return bookList;
        }
        int from = startPosition == null ? 0 : startPosition;
        int end = from + (maxResult == null ? 0 : maxResult);
        return bookList.subList(from, end);

        /*
         * this works for cases when there is no lazy loading...
         * 
         * CriteriaQuery<Book> criteria = this.em.getCriteriaBuilder()
         * .createQuery(Book.class); TypedQuery<Book> findAllQuery =
         * this.em.createQuery(criteria .select(criteria.from(Book.class)));
         * 
         * if (startPosition != null) {
         * findAllQuery.setFirstResult(startPosition); } if (maxResult != null)
         * { findAllQuery.setMaxResults(maxResult); }
         * 
         * final List<Book> results = findAllQuery.getResultList(); return
         * results;
         */
    }
}
