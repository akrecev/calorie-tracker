package com.kretsev.calorietracker.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.kretsev.calorietracker.dto.MealDto;
import com.kretsev.calorietracker.mapper.MealMapper;
import com.kretsev.calorietracker.model.Dish;
import com.kretsev.calorietracker.model.Goal;
import com.kretsev.calorietracker.model.Meal;
import com.kretsev.calorietracker.model.User;
import com.kretsev.calorietracker.repository.DishRepository;
import com.kretsev.calorietracker.repository.MealRepository;
import com.kretsev.calorietracker.repository.UserRepository;
import com.kretsev.calorietracker.service.impl.MealServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {
    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private EntityService entityService;

    @Mock
    private MealMapper mealMapper;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private MealServiceImpl mealService;

    @Test
    void testCreateMeal() {
        Long userId = 1L;
        User user = new User(userId, "Alice", "alice@example.com", 30, 65.0, 170.0, Goal.MAINTENANCE, 2000);
        Dish dish = new Dish(1L, "Salad", 300, 10, 5, 20);
        MealDto mealDto = new MealDto(null, userId, LocalDateTime.now(), List.of(1L));
        Meal meal = new Meal(null, user, mealDto.mealDate(), List.of(dish));

        when(entityService.findEntityOrElseThrow(userRepository, userId, "Пользователь не найден"))
                .thenReturn(user);
        when(dishRepository.findAllById(mealDto.dishIds())).thenReturn(List.of(dish));
        when(mealRepository.save(any())).thenReturn(meal);
        when(mealMapper.toDto(meal)).thenReturn(new MealDto(meal.getId(), userId, meal.getMealDate(), List.of(1L)));

        MealDto result = mealService.createMeal(mealDto);

        assertNotNull(result);
        assertEquals(userId, result.userId());
        assertEquals(1, result.dishIds().size());
    }

    @Test
    void testUpdateMeal() {
        Long mealId = 1L;
        User user = new User(1L, "Bob", "bob@example.com", 28, 80.0, 180.0, Goal.MAINTENANCE, 2500);
        Dish dish = new Dish(1L, "Pasta", 500, 15, 10, 60);
        Meal meal = new Meal(mealId, user, LocalDateTime.now(), List.of(dish));
        MealDto updatedMealDto = new MealDto(mealId, user.getId(), meal.getMealDate(), List.of(1L));

        when(entityService.findEntityOrElseThrow(mealRepository, mealId, "Приём пищи не найден"))
                .thenReturn(meal);
        when(dishRepository.findAllById(updatedMealDto.dishIds())).thenReturn(List.of(dish));
        when(mealRepository.save(meal)).thenReturn(meal);
        when(mealMapper.toDto(meal)).thenReturn(updatedMealDto);

        MealDto result = mealService.updateMeal(mealId, updatedMealDto);

        assertNotNull(result);
        assertEquals(mealId, result.id());
        assertEquals(1, result.dishIds().size());
    }
}
