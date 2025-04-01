package com.kretsev.calorietracker.service.impl;

import com.kretsev.calorietracker.dto.MealDto;
import com.kretsev.calorietracker.mapper.MealMapper;
import com.kretsev.calorietracker.model.Dish;
import com.kretsev.calorietracker.model.Meal;
import com.kretsev.calorietracker.model.User;
import com.kretsev.calorietracker.repository.DishRepository;
import com.kretsev.calorietracker.repository.MealRepository;
import com.kretsev.calorietracker.repository.UserRepository;
import com.kretsev.calorietracker.service.EntityService;
import com.kretsev.calorietracker.service.LoggingService;
import com.kretsev.calorietracker.service.MealService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final MealMapper mealMapper;
    private final EntityService entityService;
    private final LoggingService loggingService;

    @Override
    @Transactional
    public MealDto createMeal(MealDto mealDto) {
        User user = entityService.findEntityOrElseThrow(userRepository, mealDto.userId(), "Пользователь не найден");
        List<Dish> dishes = dishRepository.findAllById(mealDto.dishIds());

        Meal meal = new Meal();
        meal.setUser(user);
        meal.setDishes(dishes);
        meal.setMealDate(mealDto.mealDate());

        Meal savedMeal = mealRepository.save(meal);
        loggingService.logInfo("Создан приём пищи: id={}, userId={}", savedMeal.getId(), user.getId());
        return mealMapper.toDto(savedMeal);
    }

    @Override
    public MealDto getMealById(Long id) {
        return mealMapper.toDto(getMealInner(id));
    }

    @Override
    public Page<MealDto> getMealsByUserId(Long userId, int page, int size) {
        return mealRepository.findByUserId(userId, PageRequest.of(page, size)).map(mealMapper::toDto);
    }

    @Override
    @Transactional
    public MealDto updateMeal(Long id, MealDto mealDto) {
        Meal meal = getMealInner(id);
        List<Dish> dishes = dishRepository.findAllById(mealDto.dishIds());

        meal.setDishes(dishes);
        meal.setMealDate(mealDto.mealDate());

        loggingService.logInfo("Приём пищи обновлён: id={}", meal.getId());
        return mealMapper.toDto(mealRepository.save(meal));
    }

    @Override
    @Transactional
    public void deleteMeal(Long id) {
        Meal meal = getMealInner(id);
        mealRepository.delete(meal);
        loggingService.logInfo("Удален приём пищи: id={}", meal.getId());
    }

    private Meal getMealInner(Long id) {
        return entityService.findEntityOrElseThrow(mealRepository, id, "Приём пищи не найден");
    }
}
