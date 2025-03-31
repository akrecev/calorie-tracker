package com.kretsev.calorietracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO for {@link com.kretsev.calorietracker.model.Dish}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DishDto(
        Long id,
        @NotBlank(message = "Название блюда не должно быть пустым") String name,
        @PositiveOrZero(message = "Количество калорий не может быть отрицательным") int calories,
        @PositiveOrZero(message = "Количество протеинов не может быть отрицательным") double proteins,
        @PositiveOrZero(message = "Количество жиров не может быть отрицательным") double fats,
        @PositiveOrZero(message = "Количество углеводов не может быть отрицательным") double carbs) {}
