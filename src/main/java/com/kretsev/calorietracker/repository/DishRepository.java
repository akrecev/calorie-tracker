package com.kretsev.calorietracker.repository;

import com.kretsev.calorietracker.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {}
