package com.kretsev.calorietracker.controller;

import com.kretsev.calorietracker.dto.MealDto;
import com.kretsev.calorietracker.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing meals in the Calorie Tracker application.
 */
@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    /**
     * Creates a new meal.
     *
     * @param mealDto the meal data transfer object containing meal details
     * @return a ResponseEntity containing the created meal DTO and HTTP status 201
     */
    @PostMapping
    public ResponseEntity<MealDto> createMeal(@RequestBody @Valid MealDto mealDto) {
        MealDto savedMeal = mealService.createMeal(mealDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMeal);
    }

    /**
     * Retrieves a meal by its ID.
     *
     * @param id the ID of the meal to retrieve
     * @return a ResponseEntity containing the meal DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<MealDto> getMealById(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    /**
     * Retrieves a paginated list of meals for a specific user.
     *
     * @param userId the ID of the user whose meals are to be retrieved
     * @param page the page number (default is 0)
     * @param size the number of items per page (default is 10)
     * @return a ResponseEntity containing a page of meal DTOs
     */
    @GetMapping
    public ResponseEntity<Page<MealDto>> getMeals(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(mealService.getMealsByUserId(userId, page, size));
    }

    /**
     * Updates an existing meal.
     *
     * @param id the ID of the meal to update
     * @param mealDto the updated meal data transfer object
     * @return a ResponseEntity containing the updated meal DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<MealDto> updateMeal(@PathVariable Long id, @RequestBody @Valid MealDto mealDto) {
        return ResponseEntity.ok(mealService.updateMeal(id, mealDto));
    }

    /**
     * Deletes a meal by its ID.
     *
     * @param id the ID of the meal to delete
     * @return a ResponseEntity with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}
