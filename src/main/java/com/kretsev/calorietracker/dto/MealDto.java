package com.kretsev.calorietracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.kretsev.calorietracker.model.Meal}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MealDto(Long id, @NotNull Long userId, LocalDateTime mealDate, @NotEmpty List<Long> dishIds) {}
