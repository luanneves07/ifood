package com.github.luanneves07.ifood.registration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.luanneves07.ifood.registration.dto.RestaurantDto;
import com.github.luanneves07.ifood.registration.mapper.RestaurantMapper;

@Path("/restaurants")
@Tag(name = "Restaurant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {

	private RestaurantMapper restaurantMapper = RestaurantMapper.INSTANCE;

	@GET
	public List<RestaurantDto> list() {
		return Restaurant.streamAll().map(t -> {
			return restaurantMapper.restaurantToRestaurantDto((Restaurant) t);
		}).collect(Collectors.toList());
	}

	@POST
	@Transactional
	public Response create(RestaurantDto dto) {
		Restaurant restaurant = restaurantMapper.toRestaurant(dto);
		restaurant.persist();
		return Response.ok(dto, MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public Response update(@PathParam("id") Long id, RestaurantDto dto) {
		Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(id);
		if (restaurantOp.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			Restaurant restaurant = restaurantOp.get();
			restaurantMapper.toRestaurant(dto, restaurant);
			restaurant.persist();
			return Response.ok(dto, MediaType.APPLICATION_JSON).build();
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
