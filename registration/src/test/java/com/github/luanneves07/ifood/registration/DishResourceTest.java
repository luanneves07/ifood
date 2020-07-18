package com.github.luanneves07.ifood.registration;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ws.rs.core.Response.Status;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.luanneves07.ifood.registration.dto.DishDto;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@DBRider
@QuarkusTest
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTestResource(RegistrationTestLifeCycleManager.class)
public class DishResourceTest {
	
	private RequestSpecification given() {
		return RestAssured.given().contentType(ContentType.JSON);
	}

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
		DishDto dto = new DishDto();
		dto.name = "potato ahh";
		dto.price = new BigDecimal(150);
		
		String res = given()
			.with().pathParam("idRestaurant", idRestaurant)
			.body(dto)
			.when().post("/restaurants/{idRestaurant}/dishes")
			.then()
			.statusCode(Status.OK.getStatusCode())
			.extract().asString();
		
		Dish found = Dish.find("name", dto.name).firstResult();
		assertEquals(dto.name, found.name);
		Approvals.verifyJson(res);
	}

	@Test
	@DataSet("dish-01.yml")
	public void testUpdateRestaurants() {
		Long id = 456L;
		Long idRestaurant = 2L;
		DishDto dto = new DishDto();
		dto.name = "test01";
		dto.description = "Luan";
		dto.price = new BigDecimal(150);
		
		String res = given()
			.with().pathParams("idRestaurant", idRestaurant, "id", id)
			.body(dto)
			.when().put("/restaurants/{idRestaurant}/dishes/{id}")
			.then()
			.statusCode(Status.OK.getStatusCode())
			.extract().asString();
		
		Dish found = Dish.findById(id);
		assertEquals(dto.name, found.name);
		Approvals.verifyJson(res);
	}
	
	@Test
	@DataSet("dish-01.yml")
	public void testDeleteRestaurants() {
		Long id = 123L;
		Long idRestaurant = 2L;
		given()
			.with().pathParams("idRestaurant", idRestaurant, "id", id)
			.when().delete("/restaurants/{idRestaurant}/dishes/{id}")
			.then()
			.statusCode(Status.OK.getStatusCode())
			.extract().asString();
		
		Optional<Dish> found = Dish.findByIdOptional(id);
		assertEquals(true, found.isEmpty());
	}
}