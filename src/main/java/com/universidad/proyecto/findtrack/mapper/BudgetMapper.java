package com.universidad.proyecto.findtrack.mapper;

import java.math.BigDecimal;
import java.util.UUID;

import com.universidad.proyecto.findtrack.dto.request.BudgetRequestDTO;
import com.universidad.proyecto.findtrack.dto.response.BudgetResponseDTO;
import com.universidad.proyecto.findtrack.model.Budget;

public class BudgetMapper {

    public static BudgetResponseDTO toDTO(Budget budget, BigDecimal spentAmount, BigDecimal usagePercentage,
            boolean alertTriggered, String categoryName) {


        return new BudgetResponseDTO(
                budget.getId(),
                new BudgetResponseDTO.CategorySummaryDTO(budget.getCategoryId(), categoryName),
                budget.getLimitAmount(),
                budget.getMonth(),
                budget.getYear(),
                budget.getAlertThreshold(),
                spentAmount,
                usagePercentage,
                alertTriggered);
    }

    public static Budget toEntity(UUID userId, BigDecimal alertThreshold, BudgetRequestDTO budgetRequest) {
        return Budget.builder()
                .userId(userId)
                .categoryId(budgetRequest.getCategoryId())
                .limitAmount(budgetRequest.getLimitAmount())
                .month(budgetRequest.getMonth())
                .year(budgetRequest.getYear())
                .alertThreshold(alertThreshold)
                .build();
    }

}
