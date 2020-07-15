package com.github.luanneves07.ifood.registration;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {

	@GET
	public List<Restaurant> list() {
		return Restaurant.listAll();
	}

	@POST
	@Transactional
	public Response create(Restaurant dto) {
		dto.persist();
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public Response update(@PathParam("id") Long id, Restaurant dto) {
		Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(id);
		if (restaurantOp.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			Restaurant restaurant = restaurantOp.get();
			restaurant.name = dto.name;
			restaurant.owner = dto.owner;
			restaurant.persist();
			return Response.status(Status.OK).build();
		}
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response delete(@PathParam("id") Long id) {
		Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(id);
		if (restaurantOp.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			restaurantOp.get().delete();
			return Response.status(Status.OK).build();
		}
	}
}
