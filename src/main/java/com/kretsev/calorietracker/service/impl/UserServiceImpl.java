package com.kretsev.calorietracker.service.impl;

import com.kretsev.calorietracker.dto.CalorieCheckDto;
import com.kretsev.calorietracker.dto.DailyReportDto;
import com.kretsev.calorietracker.dto.UserDto;
import com.kretsev.calorietracker.mapper.UserMapper;
import com.kretsev.calorietracker.model.Dish;
import com.kretsev.calorietracker.model.Meal;
import com.kretsev.calorietracker.model.User;
import com.kretsev.calorietracker.repository.MealRepository;
import com.kretsev.calorietracker.repository.UserRepository;
import com.kretsev.calorietracker.service.EntityService;
import com.kretsev.calorietracker.service.LoggingService;
import com.kretsev.calorietracker.service.UserService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing users and their reports in the Calorie Tracker application.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final UserMapper userMapper;
    private final EntityService entityService;
    private final LoggingService loggingService;

    /**
     * Creates a new user.
     *
     * @param userDTO the user data transfer object containing user details
     * @return the created user DTO
     */
    @Override
    @Transactional
    public UserDto createUser(UserDto userDTO) {
        User user = userMapper.toEntity(userDTO);
        loggingService.logInfo("Пользователь успешно создан: id={}, email={}", user.getId(), user.getEmail());

        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user DTO
     */
    @Override
    public UserDto getUserById(Long id) {
        return userMapper.toDto(getUserInner(id));
    }

    /**
     * Retrieves a paginated list of all users.
     *
     * @param page the page number
     * @param size the number of items per page
     * @return a page of user DTOs
     */
    @Override
    public Page<UserDto> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size)).map(userMapper::toDto);
    }

    /**
     * Generates a daily report for a user on a specific date.
     *
     * @param userId the ID of the user
     * @param date the date for the report
     * @return the daily report DTO
     */
    @Override
    public DailyReportDto getDailyReport(Long userId, LocalDate date) {
        getUserInner(userId);
        List<Meal> meals = mealRepository.findByUserIdAndMealDateBetween(
                userId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());

        int totalCalories = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(Dish::getCalories)
                .sum();

        return new DailyReportDto(date, totalCalories, meals.size());
    }

    /**
     * Checks if a user's calorie intake is within their daily norm for a specific date.
     *
     * @param userId the ID of the user
     * @param date the date to check
     * @return the calorie check DTO
     */
    @Override
    public CalorieCheckDto checkCalorieNorm(Long userId, LocalDate date) {
        DailyReportDto report = getDailyReport(userId, date);
        User user = getUserInner(userId);

        boolean withinNorm = report.totalCalories() <= user.getDailyCalorieNorm();

        return new CalorieCheckDto(date, report.totalCalories(), user.getDailyCalorieNorm(), withinNorm);
    }

    /**
     * Retrieves the nutrition history for a user over a date range.
     *
     * @param userId the ID of the user
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of daily report DTOs
     */
    @Override
    public List<DailyReportDto> getNutritionHistory(Long userId, LocalDate startDate, LocalDate endDate) {
        return startDate
                .datesUntil(endDate.plusDays(1))
                .map(date -> getDailyReport(userId, date))
                .toList();
    }

    /**
     * Updates an existing user.
     *
     * @param id the ID of the user to update
     * @param userDto the updated user data transfer object
     * @return the updated user DTO
     */
    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = getUserInner(id);
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setAge(userDto.age());
        user.setWeight(userDto.weight());
        user.setHeight(userDto.height());
        user.setGoal(userDto.goal());
        loggingService.logInfo("Пользователь обновлен: id={}, email={}", user.getId(), user.getEmail());

        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserInner(id);
        userRepository.deleteById(id);
        loggingService.logInfo("Пользователь удален: id={}, email={}", user.getId(), user.getEmail());
    }

    /**
     * Retrieves a user entity by its ID or throws an exception if not found.
     *
     * @param id the ID of the user
     * @return the user entity
     */
    private User getUserInner(Long id) {
        return entityService.findEntityOrElseThrow(userRepository, id, "Пользователь не найден");
    }
}
