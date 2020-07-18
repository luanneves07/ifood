package com.github.luanneves07.ifood.registration;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import javax.ws.rs.core.Response.Status;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.luanneves07.ifood.registration.dto.RestaurantDto;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@DBRider
@QuarkusTest
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTestResource(RegistrationTestLifeCycleManager.class)
public class RestaurantResourceTest {
	
	private RequestSpecification given() {
		return RestAssured.given().contentType(ContentType.JSON);
	}

	@Test
	@DataSet("restaurant-01.yml")
	public void testListRestaurants() {
		String res = given().when().get("/restaurants").then().statusCode(200).extract().asString();
		Approvals.verifyJson(res);
	}

	@Test
	public void testCreateRestaurant() {
		RestaurantDto dto = new RestaurantDto();
		dto.tradingName = "test01";
		dto.owner = "Luan";

		String res = given()
			.body(dto)
			.when().post("/restaurants")
			.then()
			.statusCode(Status.OK.getStatusCode())
			.extract().asString();
		
		Restaurant found = Restaurant.find("name", dto.tradingName).firstResult();
		assertEquals(dto.tradingName, found.name);
		Approvals.verifyJson(res);
	}

	@Test
	@DataSet("restaurant-01.yml")
	public void testUpdateRestaurant() {
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.tradingName = "test01";
		restaurant.owner = "Luan";
		Long id = 456L;
		
		String res = given()
			.with().pathParam("id", id)
			.body(restaurant)
			.when().put("/restaurants/{id}")
			.then()
			.statusCode(Status.OK.getStatusCode())
			.extract().asString();
		
		Restaurant found = Restaurant.findById(id);
		assertEquals(restaurant.tradingName, found.name);
		Approvals.verifyJson(res);
	}
	
	@Test
	@DataSet("restaurant-01.yml")
	public void testDeleteRestaurant() {
		Long id = 123L;
		
		given()
			.with().pathParam("id", id)
			.when().delete("/restaurants/{id}")
			.then()
			.statusCode(Status.OK.getStatusCode())
			.extract().asString();
		
		Optional<Restaurant> found = Restaurant.findByIdOptional(id);
		assertEquals(true, found.isEmpty());
	}
}