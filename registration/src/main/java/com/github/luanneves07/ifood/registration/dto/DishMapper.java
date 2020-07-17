package com.github.luanneves07.ifood.registration.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.github.luanneves07.ifood.registration.Dish;

@Mapper
public interface DishMapper {
	
	public static final DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);

	public DishDto dishToDishDto(Dish dish);

	public Dish toDish(DishDto dishDto);
}
