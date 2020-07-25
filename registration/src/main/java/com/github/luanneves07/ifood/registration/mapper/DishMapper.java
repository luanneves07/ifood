package com.github.luanneves07.ifood.registration.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.github.luanneves07.ifood.registration.Dish;
import com.github.luanneves07.ifood.registration.dto.CreateDishDto;
import com.github.luanneves07.ifood.registration.dto.DishDto;
import com.github.luanneves07.ifood.registration.dto.UpdateDishDto;

@Mapper
public interface DishMapper {

	public static final DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);

	public DishDto dishToDishDto(Dish dish);

	public Dish toDish(CreateDishDto dishDto);

	public void toDish(UpdateDishDto dto, @MappingTarget Dish dish);
}
