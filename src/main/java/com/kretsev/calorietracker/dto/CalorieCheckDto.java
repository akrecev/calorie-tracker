package com.kretsev.calorietracker.dto;

import java.time.LocalDate;

public record CalorieCheckDto(LocalDate date, int totalCalories, int dailyNorm, boolean withinNorm) {}
