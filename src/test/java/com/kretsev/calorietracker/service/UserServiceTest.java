package com.kretsev.calorietracker.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.kretsev.calorietracker.dto.CalorieCheckDto;
import com.kretsev.calorietracker.dto.DailyReportDto;
import com.kretsev.calorietracker.mapper.UserMapper;
import com.kretsev.calorietracker.model.Dish;
import com.kretsev.calorietracker.model.Goal;
import com.kretsev.calorietracker.model.Meal;
import com.kretsev.calorietracker.model.User;
import com.kretsev.calorietracker.repository.MealRepository;
import com.kretsev.calorietracker.repository.UserRepository;
import com.kretsev.calorietracker.service.impl.UserServiceImpl;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private EntityService entityService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private static final Long USER_ID = 1L;
    private static final LocalDate DATE = LocalDate.of(2025, 4, 1);

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(USER_ID, "Test User", "test@example.com", 30, 70.0, 175.0, Goal.MAINTENANCE, 2000);

        when(entityService.findEntityOrElseThrow(userRepository, USER_ID, "Пользователь не найден"))
                .thenReturn(user);
    }

    @Test
    void testGetDailyReport() {
        Meal meal = createMeal("Salad", 300);
        when(mealRepository.findByUserIdAndMealDateBetween(any(), any(), any())).thenReturn(List.of(meal));

        DailyReportDto report = userService.getDailyReport(USER_ID, DATE);

        assertEquals(300, report.totalCalories());
        assertEquals(1, report.mealsCount());
    }

    @Test
    void testCheckCalorieNormWithinLimit() {
        user.setDailyCalorieNorm(2500);
        Meal meal = createMeal("Pasta", 500);

        when(mealRepository.findByUserIdAndMealDateBetween(any(), any(), any())).thenReturn(List.of(meal));

        CalorieCheckDto result = userService.checkCalorieNorm(USER_ID, DATE);

        assertTrue(result.withinNorm());
        assertEquals(500, result.totalCalories());
        assertEquals(2500, result.dailyNorm());
    }

    @Test
    void testCheckCalorieNormExceeded() {
        user.setDailyCalorieNorm(2000);
        Meal meal = createMeal("Burger", 1500, "Fries", 800);

        when(mealRepository.findByUserIdAndMealDateBetween(any(), any(), any())).thenReturn(List.of(meal));

        CalorieCheckDto result = userService.checkCalorieNorm(USER_ID, DATE);

        assertFalse(result.withinNorm());
        assertEquals(2300, result.totalCalories());
    }

    private Meal createMeal(String dishName, int calories) {
        return new Meal(1L, user, DATE.atTime(12, 0), List.of(new Dish(1L, dishName, calories, 10, 5, 20)));
    }

    private Meal createMeal(String dish1, int cal1, String dish2, int cal2) {
        return new Meal(
                1L,
                user,
                DATE.atTime(12, 0),
                List.of(new Dish(1L, dish1, cal1, 10, 5, 20), new Dish(2L, dish2, cal2, 5, 3, 10)));
    }
}
