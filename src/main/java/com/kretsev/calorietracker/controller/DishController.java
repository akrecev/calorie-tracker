package com.kretsev.calorietracker.controller;

import com.kretsev.calorietracker.dto.DishDto;
import com.kretsev.calorietracker.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing dishes in the Calorie Tracker application.
 */
@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    /**
     * Creates a new dish.
     *
     * @param dishDto the dish data transfer object containing dish details
     * @return a ResponseEntity containing the created dish DTO and HTTP status 201
     */
    @PostMapping
    public ResponseEntity<DishDto> createDish(@RequestBody @Valid DishDto dishDto) {
        DishDto savedDish = dishService.createDish(dishDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDish);
    }

    /**
     * Retrieves a dish by its ID.
     *
     * @param id the ID of the dish to retrieve
     * @return a ResponseEntity containing the dish DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<DishDto> getDishById(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    /**
     * Retrieves a paginated list of all dishes.
     *
     * @param page the page number (default is 0)
     * @param size the number of items per page (default is 10)
     * @return a ResponseEntity containing a page of dish DTOs
     */
    @GetMapping
    public ResponseEntity<Page<DishDto>> getAllDishes(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(dishService.getAllDishes(page, size));
    }

    /**
     * Updates an existing dish.
     *
     * @param id the ID of the dish to update
     * @param dishDto the updated dish data transfer object
     * @return a ResponseEntity containing the updated dish DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<DishDto> updateDish(@PathVariable Long id, @RequestBody @Valid DishDto dishDto) {
        return ResponseEntity.ok(dishService.updateDish(id, dishDto));
    }

    /**
     * Deletes a dish by its ID.
     *
     * @param id the ID of the dish to delete
     * @return a ResponseEntity with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }
}
