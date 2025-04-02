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

/**
 * Service implementation for managing meals in the Calorie Tracker application.
 */
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

    /**
     * Creates a new meal.
     *
     * @param mealDto the meal data transfer object containing meal details
     * @return the created meal DTO
     */
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

    /**
     * Retrieves a meal by its ID.
     *
     * @param id the ID of the meal to retrieve
     * @return the meal DTO
     */
    @Override
    public MealDto getMealById(Long id) {
        return mealMapper.toDto(getMealInner(id));
    }

    /**
     * Retrieves a paginated list of meals for a specific user.
     *
     * @param userId the ID of the user
     * @param page the page number
     * @param size the number of items per page
     * @return a page of meal DTOs
     */
    @Override
    public Page<MealDto> getMealsByUserId(Long userId, int page, int size) {
        return mealRepository.findByUserId(userId, PageRequest.of(page, size)).map(mealMapper::toDto);
    }

    /**
     * Updates an existing meal.
     *
     * @param id the ID of the meal to update
     * @param mealDto the updated meal data transfer object
     * @return the updated meal DTO
     */
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

    /**
     * Deletes a meal by its ID.
     *
     * @param id the ID of the meal to delete
     */
    @Override
    @Transactional
    public void deleteMeal(Long id) {
        Meal meal = getMealInner(id);
        mealRepository.delete(meal);
        loggingService.logInfo("Удален приём пищи: id={}", meal.getId());
    }

    /**
     * Retrieves a meal entity by its ID or throws an exception if not found.
     *
     * @param id the ID of the meal
     * @return the meal entity
     */
    private Meal getMealInner(Long id) {
        return entityService.findEntityOrElseThrow(mealRepository, id, "Приём пищи не найден");
    }
}
