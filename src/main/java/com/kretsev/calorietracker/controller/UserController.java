package com.kretsev.calorietracker.controller;

import com.kretsev.calorietracker.dto.CalorieCheckDto;
import com.kretsev.calorietracker.dto.DailyReportDto;
import com.kretsev.calorietracker.dto.UserDto;
import com.kretsev.calorietracker.service.UserService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing users and their reports in the Calorie Tracker application.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Creates a new user.
     *
     * @param userDTO the user data transfer object containing user details
     * @return a ResponseEntity containing the created user DTO
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return a ResponseEntity containing the user DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Retrieves a paginated list of all users.
     *
     * @param page the page number (default is 0)
     * @param size the number of items per page (default is 10)
     * @return a ResponseEntity containing a page of user DTOs
     */
    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<UserDto> users = userService.getAllUsers(page, size);
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a daily report for a user on a specific date.
     *
     * @param id the ID of the user
     * @param date the date for the report
     * @return a ResponseEntity containing the daily report DTO
     */
    @GetMapping("/{id}/report/daily")
    public ResponseEntity<DailyReportDto> getDailyReport(@PathVariable Long id, @RequestParam LocalDate date) {
        return ResponseEntity.ok(userService.getDailyReport(id, date));
    }

    /**
     * Checks if a user's calorie intake is within their daily norm for a specific date.
     *
     * @param id the ID of the user
     * @param date the date to check
     * @return a ResponseEntity containing the calorie check DTO
     */
    @GetMapping("/{id}/report/check")
    public ResponseEntity<CalorieCheckDto> checkCalorieNorm(@PathVariable Long id, @RequestParam LocalDate date) {
        return ResponseEntity.ok(userService.checkCalorieNorm(id, date));
    }

    /**
     * Retrieves the nutrition history for a user over a date range.
     *
     * @param id the ID of the user
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a ResponseEntity containing a list of daily report DTOs
     */
    @GetMapping("/{id}/report/history")
    public ResponseEntity<List<DailyReportDto>> getNutritionHistory(
            @PathVariable Long id, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(userService.getNutritionHistory(id, startDate, endDate));
    }

    /**
     * Updates an existing user.
     *
     * @param id the ID of the user to update
     * @param userDTO the updated user data transfer object
     * @return a ResponseEntity containing the updated user DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return a ResponseEntity with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
