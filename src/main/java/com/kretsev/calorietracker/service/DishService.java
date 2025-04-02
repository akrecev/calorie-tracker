package com.kretsev.calorietracker.service;

import com.kretsev.calorietracker.dto.DishDto;
import org.springframework.data.domain.Page;

public interface DishService {
    DishDto createDish(DishDto dishDto);

    DishDto getDishById(Long id);

    Page<DishDto> getAllDishes(int page, int size);

    DishDto updateDish(Long id, DishDto dishDto);

    void deleteDish(Long id);
}
