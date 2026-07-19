package com.universidad.proyecto.findtrack.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.universidad.proyecto.findtrack.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    
    boolean existsByUserIdAndCategoryIdAndMonthAndYear(UUID userId, UUID categoryId, Integer month, Integer year);

    List<Budget> findByUserIdAndMonthAndYear(UUID userId, Integer month, Integer year);
}
