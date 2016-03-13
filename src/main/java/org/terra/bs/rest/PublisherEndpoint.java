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

import org.terra.bs.ejb.dao.PublisherDao;
import org.terra.bs.entities.Publisher;

/**
 * 
 */
@Stateless
@Path("/publishers")
public class PublisherEndpoint {
    @Inject
    private PublisherDao em;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(Publisher entity) {
        em.create(entity);
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") int id) {
        em.delete(id);

        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces("application/json")
    public Response findById(@PathParam("id") int id) {

        Publisher entity = this.em.findById(id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    @Produces("application/json")
    public List<Publisher> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {

        return this.em.listAll(startPosition, maxResult);
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes("application/json")
    public Response update(Publisher entity) {
        em.update(entity);
        return Response.noContent().build();
    }
}