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
        @Min(value = 0L, message = "Возраст не может быть отрицательным")
                @Max(value = 150, message = "Возраст должен быть от 0 до 150 лет")
                int age,
        @Min(value = 0L, message = "Вес не может быть отрицательным")
                @Max(value = 350L, message = "Вес должен быть от 0 до 350 кг")
                double weight,
        @Min(value = 0L, message = "Рост не может быть отрицательным")
                @Max(value = 250L, message = "Рост должен быть от 0 до 250 см")
                double height,
        Goal goal,
        int dailyCalorieNorm) {}
