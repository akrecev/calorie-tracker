package com.kretsev.calorietracker.service;

import com.kretsev.calorietracker.dto.MealDto;
import org.springframework.data.domain.Page;

public interface MealService {

    MealDto createMeal(MealDto mealDto);

    MealDto getMealById(Long id);

    Page<MealDto> getMealsByUserId(Long userId, int page, int size);

    MealDto updateMeal(Long id, MealDto mealDto);

    void deleteMeal(Long id);
}
