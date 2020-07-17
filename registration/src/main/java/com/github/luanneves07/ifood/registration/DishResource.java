package com.github.luanneves07.ifood.registration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.luanneves07.ifood.registration.dto.DishDto;
import com.github.luanneves07.ifood.registration.dto.DishMapper;

@Tag(name = "Dish")
@Path("/restaurants/{idRestaurant}/dishes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DishResource {

	private DishMapper dishMapper = DishMapper.INSTANCE;

	@GET
	@Tag(name = "Dish")
	public List<DishDto> list(@PathParam("idRestaurant") Long idRestaurant) {
		Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
		if (restaurantOp.isEmpty()) {
			throw new NotFoundException("Retaurant does not exists!");
		}
		return Dish.list("restaurant", restaurantOp.get()).stream().map(t -> dishMapper.dishToDishDto((Dish) t))
				.collect(Collectors.toList());
	}

	@POST
	@Transactional
	@Tag(name = "Dish")
	public Response create(@PathParam("idRestaurant") Long idRestaurant, DishDto dto) {
		Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
		if (restaurantOp.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Dish dish = dishMapper.toDish(dto);
		dish.restaurant = restaurantOp.get();
		dish.persist();
		return Response.ok(dish).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	@Tag(name = "Dish")
	public Response update(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id, DishDto dto) {
		Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
		if (restaurantOp.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Optional<Dish> dishOp = Dish.findByIdOptional(id);
		if (dishOp.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Dish dish = dishOp.get();
		dishMapper.toDish(dto, dish);
		dish.persist();
		return Response.ok(dish).build();
	}

	@DELETE
	@Path("{id}")
	@Transactional
	@Tag(name = "Dish")
	public Response delete(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id) {
		Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
		if (restaurantOp.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Optional<Dish> dishOp = Dish.findByIdOptional(id);
		if (dishOp.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}

		dishOp.get().delete();
		return Response.status(Status.OK).build();
	}
}
