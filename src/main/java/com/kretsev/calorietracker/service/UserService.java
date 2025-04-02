package com.kretsev.calorietracker.service;

import com.kretsev.calorietracker.dto.CalorieCheckDto;
import com.kretsev.calorietracker.dto.DailyReportDto;
import com.kretsev.calorietracker.dto.UserDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;

public interface UserService {
    UserDto createUser(UserDto userDTO);

    UserDto getUserById(Long id);

    Page<UserDto> getAllUsers(int page, int size);

    DailyReportDto getDailyReport(Long userId, LocalDate date);

    CalorieCheckDto checkCalorieNorm(Long userId, LocalDate date);

    List<DailyReportDto> getNutritionHistory(Long userId, LocalDate startDate, LocalDate endDate);

    UserDto updateUser(Long id, UserDto userDTO);

    void deleteUser(Long id);
}
