package com.kretsev.calorietracker.repository;

import com.kretsev.calorietracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
