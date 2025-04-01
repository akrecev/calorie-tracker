package com.kretsev.calorietracker.dto;

import java.time.LocalDate;

public record DailyReportDto(LocalDate date, int totalCalories, int mealsCount) {}
