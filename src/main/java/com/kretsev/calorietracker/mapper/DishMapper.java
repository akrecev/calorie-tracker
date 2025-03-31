package com.kretsev.calorietracker.mapper;

import com.kretsev.calorietracker.dto.DishDto;
import com.kretsev.calorietracker.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishMapper {
    Dish toEntity(DishDto dishDto);

    DishDto toDishDto(Dish dish);
}
