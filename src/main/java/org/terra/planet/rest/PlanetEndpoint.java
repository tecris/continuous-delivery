package org.terra.planet.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

import org.terra.planet.dao.PlanetDao;
import org.terra.planet.model.Planet;

/**
 * 
 */
@Stateless
@Path("/planet")
public class PlanetEndpoint {


    @Inject
    private PlanetDao authorDao;
    
    @PersistenceContext(unitName = "pl-persistence-unit")
    private EntityManager em;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(Planet entity) {
    	 authorDao.create(entity);
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") Long id) {
        this.authorDao.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces("application/json")
    public Response findById(@PathParam("id") Long id) {
        Planet entity = this.authorDao.findById(id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    @Produces("application/json")
    public List<Planet> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {
    	return this.authorDao.listAll(startPosition, maxResult);
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes("application/json")
    public Response update(@PathParam("id") Long id, Planet entity) {
        if (entity == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        if (id == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        if (!id.equals(entity.getId())) {
            return Response.status(Status.CONFLICT).entity(entity).build();
        }
        if (em.find(Planet.class, id) == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        try {
        	this.authorDao.update(entity);
        } catch (OptimisticLockException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
        }

        return Response.noContent().build();
    }
}
