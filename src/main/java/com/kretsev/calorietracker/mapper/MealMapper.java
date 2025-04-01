package com.kretsev.calorietracker.mapper;

import com.kretsev.calorietracker.dto.MealDto;
import com.kretsev.calorietracker.model.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MealMapper {
    Meal toEntity(MealDto mealDto);

    MealDto toDto(Meal meal);
}
