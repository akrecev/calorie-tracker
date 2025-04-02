package com.kretsev.calorietracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Calorie Tracker Spring Boot application.
 */
@SpringBootApplication
public class CalorieTrackerApplication {
    /**
     * Starts the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CalorieTrackerApplication.class, args);
    }
}
