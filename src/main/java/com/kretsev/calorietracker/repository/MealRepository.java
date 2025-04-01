package com.kretsev.calorietracker.repository;

import com.kretsev.calorietracker.model.Meal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
    Page<Meal> findByUserId(Long userId, Pageable pageable);
}
