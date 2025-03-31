package com.kretsev.calorietracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.kretsev.calorietracker.model.Meal}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MealDto(Long id, UserDto user, LocalDateTime mealDate, List<DishDto> dishes) {
}