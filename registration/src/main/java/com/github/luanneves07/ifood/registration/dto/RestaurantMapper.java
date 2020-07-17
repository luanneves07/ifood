package com.github.luanneves07.ifood.registration.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.github.luanneves07.ifood.registration.Restaurant;

@Mapper
public interface RestaurantMapper {

	public static final RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

	@Mapping(source = "name", target = "tradingName")
	public RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

	@Mapping(source = "tradingName", target = "name")
	public Restaurant toRestaurant(RestaurantDto restaurant);

}
