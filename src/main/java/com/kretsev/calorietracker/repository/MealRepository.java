package com.kretsev.calorietracker.repository;

import com.kretsev.calorietracker.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}