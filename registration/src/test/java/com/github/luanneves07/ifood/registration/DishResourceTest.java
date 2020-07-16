package com.github.luanneves07.ifood.registration;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@DBRider
@QuarkusTest
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTestResource(RegistrationTestLifeCycleManager.class)
public class DishResourceTest {

	@Test
	@DataSet("dish-01.yml")
	public void testListDishes() {
		Long idRestaurant = 2L;
		String res = given()
				.with().pathParam("idRestaurant", idRestaurant)
				.when().get("/restaurants/{idRestaurant}/dishes")
				.then()
				.statusCode(Status.OK.getStatusCode())
				.extract().asString();
		Approvals.verifyJson(res);
	}

	@Test
	@DataSet("dish-01.yml")
	public void testCreateDish() {
		Long idRestaurant = 2L;
		Dish dish = new Dish();
		dish.name = "potato ahh";
		dish.price = new BigDecimal(150);
		
		given()
			.with().pathParam("idRestaurant", idRestaurant)
			.body(dish)
			.contentType(MediaType.APPLICATION_JSON)
			.when().post("/restaurants/{idRestaurant}/dishes")
			.then()
			.statusCode(Status.OK.getStatusCode());
		
		Dish found = Dish.find("name", dish.name).firstResult();
		assertEquals(dish.name, found.name);
	}

	@Test
	@DataSet("dish-01.yml")
	public void testUpdateRestaurants() {
		Long id = 456L;
		Long idRestaurant = 2L;
		Dish dish = new Dish();
		dish.name = "test01";
		dish.description = "Luan";
		dish.price = new BigDecimal(150);
		
		given()
			.with().pathParams("idRestaurant", idRestaurant, "id", id)
			.body(dish)
			.contentType(MediaType.APPLICATION_JSON)
			.when().put("/restaurants/{idRestaurant}/dishes/{id}")
			.then()
			.statusCode(Status.OK.getStatusCode())
			.extract().asString();
		
		Dish found = Dish.findById(id);
		assertEquals(dish.name, found.name);
	}
	
	@Test
	@DataSet("dish-01.yml")
	public void testDeleteRestaurants() {
		Long id = 123L;
		Long idRestaurant = 2L;
		given()
			.with().pathParams("idRestaurant", idRestaurant, "id", id)
			.contentType(MediaType.APPLICATION_JSON)
			.when().delete("/restaurants/{idRestaurant}/dishes/{id}")
			.then()
			.statusCode(Status.OK.getStatusCode())
			.extract().asString();
		
		Optional<Dish> found = Dish.findByIdOptional(id);
		assertEquals(true, found.isEmpty());
	}
}