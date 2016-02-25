package org.terra.bs.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.terra.bs.ejb.dao.BookDao;
import org.terra.bs.entities.Book;

/**
 * 
 */
@Stateless
@Path("/books")
public class BookEndpoint {

    @Inject
    private BookDao bookDao;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(Book entity) {
        this.bookDao.create(entity);
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") int id) {
        this.bookDao.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces("application/json")
    public Response findById(@PathParam("id") int id) {

        Book entity = this.bookDao.findById(id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    @Produces("application/json")
    public List<Book> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {

        final List<Book> results = this.bookDao.listAll(startPosition, maxResult);
        return results;
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes("application/json")
    public Response update(Book entity) {
        this.bookDao.update(entity);
        return Response.noContent().build();
    }
}