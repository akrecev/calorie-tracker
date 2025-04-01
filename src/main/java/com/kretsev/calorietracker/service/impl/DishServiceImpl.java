package com.kretsev.calorietracker.service.impl;

import com.kretsev.calorietracker.dto.DishDto;
import com.kretsev.calorietracker.mapper.DishMapper;
import com.kretsev.calorietracker.model.Dish;
import com.kretsev.calorietracker.repository.DishRepository;
import com.kretsev.calorietracker.service.DishService;
import com.kretsev.calorietracker.service.EntityService;
import com.kretsev.calorietracker.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final EntityService entityService;
    private final LoggingService loggingService;

    @Override
    @Transactional
    public DishDto createDish(DishDto dishDto) {
        Dish dish = dishMapper.toEntity(dishDto);
        Dish savedDish = dishRepository.save(dish);
        loggingService.logInfo("Создано блюдо: id={}, name={}", savedDish.getId(), savedDish.getName());
        return dishMapper.toDto(savedDish);
    }

    @Override
    public DishDto getDishById(Long id) {
        return dishMapper.toDto(getDishInner(id));
    }

    @Override
    public Page<DishDto> getAllDishes(int page, int size) {
        return dishRepository.findAll(PageRequest.of(page, size)).map(dishMapper::toDto);
    }

    @Override
    @Transactional
    public DishDto updateDish(Long id, DishDto dishDto) {
        Dish dish = getDishInner(id);
        dish.setName(dishDto.name());
        dish.setCalories(dishDto.calories());
        dish.setProteins(dishDto.proteins());
        dish.setFats(dishDto.fats());
        dish.setCarbs(dishDto.carbs());
        loggingService.logInfo("Блюдо обновлено: id={}, name={}", dish.getId(), dish.getName());
        return dishMapper.toDto(dishRepository.save(dish));
    }

    @Override
    @Transactional
    public void deleteDish(Long id) {
        Dish dish = getDishInner(id);
        dishRepository.delete(dish);
        loggingService.logInfo("Блюдо удалено: id={}, name={}", dish.getId(), dish.getName());
    }

    private Dish getDishInner(Long id) {
        return entityService.findEntityOrElseThrow(dishRepository, id, "Блюдо не найдено");
    }
}
