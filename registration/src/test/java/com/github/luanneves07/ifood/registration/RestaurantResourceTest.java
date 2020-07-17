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
		Restaurant restaurant = new Restaurant();
		restaurant.name = "test01";
		restaurant.owner = "Luan";

		given()
			.body(restaurant)
			.when().post("/restaurants")
			.then()
			.statusCode(Status.OK.getStatusCode());
		
		Restaurant found = Restaurant.find("name", restaurant.name).firstResult();
		assertEquals(restaurant.name, found.name);
	}

	@Test
	@DataSet("restaurant-01.yml")
	public void testUpdateRestaurant() {
		Restaurant restaurant = new Restaurant();
		restaurant.name = "test01";
		restaurant.owner = "Luan";
		Long id = 456L;
		
		given()
			.with().pathParam("id", id)
			.body(restaurant)
			.when().put("/restaurants/{id}")
			.then()
			.statusCode(Status.OK.getStatusCode())
			.extract().asString();
		
		Restaurant found = Restaurant.findById(id);
		assertEquals(restaurant.name, found.name);
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