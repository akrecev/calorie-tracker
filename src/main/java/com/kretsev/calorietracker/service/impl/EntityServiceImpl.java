package com.kretsev.calorietracker.service.impl;

import com.kretsev.calorietracker.service.EntityService;
import com.kretsev.calorietracker.service.LoggingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation of the EntityService for common entity operations.
 */
@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {
    private final LoggingService loggingService;

    /**
     * Finds an entity by ID or throws an exception if not found.
     *
     * @param repository the JPA repository
     * @param id the entity ID
     * @param errorMessage the error message to throw
     * @param <T> the entity type
     * @param <ID> the ID type
     * @return the found entity
     */
    @Override
    @SuppressWarnings("java:S119")
    public <T, ID> T findEntityOrElseThrow(JpaRepository<T, ID> repository, ID id, String errorMessage) {
        return repository.findById(id).orElseThrow(() -> {
            loggingService.logError("{}: id={}", errorMessage, id);
            return new EntityNotFoundException(errorMessage);
        });
    }
}
