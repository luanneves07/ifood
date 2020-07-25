package com.github.luanneves07.ifood.registration.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.github.luanneves07.ifood.registration.Restaurant;
import com.github.luanneves07.ifood.registration.dto.RestaurantDto;

@Mapper
public interface RestaurantMapper {

	public static final RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

	@Mapping(source = "name", target = "tradingName")
	public RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

	@Mapping(source = "tradingName", target = "name")
	public Restaurant toRestaurant(RestaurantDto restaurant);

	@Mapping(source = "tradingName", target = "name")
	@Mapping(target = "creationTime", dateFormat = "dd/MM/yyyy HH:mm:ss")
	public void toRestaurant(RestaurantDto dto, @MappingTarget Restaurant restaurant);

}
