package com.kretsev.calorietracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kretsev.calorietracker.model.Goal;
import com.kretsev.calorietracker.model.User;
import jakarta.validation.constraints.*;

/**
 * DTO for {@link User}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDto(
        Long id,
        @Size(message = "Имя должно содержать от 3 до 255 символов", min = 3, max = 255) String name,
        @Email(message = "Некорректный адрес электронной почты") String email,
        @Min(0L) @Max(value = 150, message = "Возраст должен быть от 0 до 150 лет") int age,
        @Min(0L) @Max(value = 350L, message = "Вес должен быть от 0 до 350 кг") double weight,
        @Min(0L) @Max(value = 250L, message = "Рост должен быть от 0 до 250 см") double height,
        Goal goal,
        int dailyCalorieNorm) {}
