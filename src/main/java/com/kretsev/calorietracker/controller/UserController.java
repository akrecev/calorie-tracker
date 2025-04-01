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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<UserDto> users = userService.getAllUsers(page, size);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/report/daily")
    public ResponseEntity<DailyReportDto> getDailyReport(@PathVariable Long id, @RequestParam LocalDate date) {
        return ResponseEntity.ok(userService.getDailyReport(id, date));
    }

    @GetMapping("/{id}/report/check")
    public ResponseEntity<CalorieCheckDto> checkCalorieNorm(@PathVariable Long id, @RequestParam LocalDate date) {
        return ResponseEntity.ok(userService.checkCalorieNorm(id, date));
    }

    @GetMapping("/{id}/report/history")
    public ResponseEntity<List<DailyReportDto>> getNutritionHistory(
            @PathVariable Long id, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(userService.getNutritionHistory(id, startDate, endDate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
