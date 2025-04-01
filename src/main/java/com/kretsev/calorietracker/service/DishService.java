package com.kretsev.calorietracker.service;

import com.kretsev.calorietracker.dto.DishDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface DishService {
    DishDto createDish(@Valid DishDto dishDto);

    DishDto getDishById(Long id);

    Page<DishDto> getAllDishes(int page, int size);

    DishDto updateDish(Long id, @Valid DishDto dishDto);

    void deleteDish(Long id);
}
