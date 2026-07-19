package com.universidad.proyecto.findtrack.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.proyecto.findtrack.dto.CategorySpent;
import com.universidad.proyecto.findtrack.dto.request.BudgetRequestDTO;
import com.universidad.proyecto.findtrack.dto.request.BudgetUpdateRequestDTO;
import com.universidad.proyecto.findtrack.dto.response.BudgetResponseDTO;
import com.universidad.proyecto.findtrack.exceptions.DuplicateBudgetException;
import com.universidad.proyecto.findtrack.exceptions.ResourceNotFoundException;
import com.universidad.proyecto.findtrack.mapper.BudgetMapper;
import com.universidad.proyecto.findtrack.model.Budget;
import com.universidad.proyecto.findtrack.model.Category;
import com.universidad.proyecto.findtrack.repository.BudgetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetService {

        private final TransactionService transactionService;

        private final CategoryService categoryService;

        private final BudgetRepository budgetRepository;

        private static final BigDecimal DEFAULT_ALERT_THRESHOLD = new BigDecimal("0.80");

        @Transactional(readOnly = true)
        public List<BudgetResponseDTO> getBudgets(UUID userId, Integer month, Integer year) {
                List<Budget> budgets = budgetRepository.findByUserIdAndMonthAndYear(userId, month, year);

                Map<UUID, BigDecimal> spentMap = buildSpentMap(userId, month, year);

                return budgets.stream()
                                .map(budget -> buildResponse(budget, spentMap))
                                .toList();
        }

        public BudgetResponseDTO createBudget(UUID userId, BudgetRequestDTO budgetRequest) {

                Category category = categoryService.getUsableCategory(budgetRequest.getCategoryId(), userId);

                if (budgetRepository.existsByUserIdAndCategoryIdAndMonthAndYear(userId, category.getId(),
                                budgetRequest.getMonth(), budgetRequest.getYear())) {
                        throw new DuplicateBudgetException(
                                        "Ya existe un presupuesto para esta categoría en el mes y año especificados.");
                }

                BigDecimal alertThreshold = calculateAlertThreshold(budgetRequest.getAlertThreshold());

                Map<UUID, BigDecimal> spentMap = buildSpentMap(userId, budgetRequest.getMonth(),
                                budgetRequest.getYear());

                Budget budget = BudgetMapper.toEntity(userId, alertThreshold, budgetRequest);

                Budget savedBudget = budgetRepository.save(budget);


                return buildResponse(savedBudget, spentMap);
        }

        public BudgetResponseDTO updateBudget(UUID budgetId, UUID userId, BudgetUpdateRequestDTO budgetUpdateRequest) {
                Budget budget = getUsableBudget(budgetId, userId);

                budget.update(budgetUpdateRequest.getLimitAmount(), budgetUpdateRequest.getAlertThreshold());

                Budget updatedBudget = budgetRepository.save(budget);

                Map<UUID, BigDecimal> spentMap = buildSpentMap(userId, updatedBudget.getMonth(),
                                updatedBudget.getYear());

                return buildResponse(updatedBudget, spentMap);
        }

        public void deleteBudget(UUID budgetId, UUID userId) {
                Budget budget = getUsableBudget(budgetId, userId);
                budgetRepository.delete(budget);
        }




        private Map<UUID, BigDecimal> buildSpentMap(UUID userId, Integer month, Integer year) {
                List<CategorySpent> spentByCategory = transactionService.getSpentByCategory(userId, month, year);

                return spentByCategory.stream()
                                .collect(Collectors.toMap(CategorySpent::categoryId, CategorySpent::spent));
        }

        private BudgetResponseDTO buildResponse(Budget budget, Map<UUID, BigDecimal> spentMap) {
                BigDecimal spentAmount = spentMap.getOrDefault(budget.getCategoryId(), BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);

                BigDecimal usagePercentage = calculateUsagePercentage(spentAmount, budget.getLimitAmount());

                boolean alertTriggered = isAlertTriggered(usagePercentage, budget.getAlertThreshold());

                return BudgetMapper.toDTO(budget, spentAmount, usagePercentage, alertTriggered);
        }

        private Budget getUsableBudget(UUID budgetId, UUID userId) {
                Budget budget = budgetRepository.findById(budgetId)
                                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado"));

                if (!budget.getUserId().equals(userId)) {
                        throw new AccessDeniedException("No tiene permiso para usar este presupuesto");
                }

                return budget;
        }

        private BigDecimal calculateAlertThreshold(BigDecimal alertThreshold) {
                return alertThreshold != null ? alertThreshold : DEFAULT_ALERT_THRESHOLD;
        }

        private BigDecimal calculateUsagePercentage(BigDecimal spent, BigDecimal limit) {
                return spent
                                .divide(limit, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100))
                                .setScale(2, RoundingMode.HALF_UP);
        }

        private boolean isAlertTriggered(BigDecimal usagePercentage, BigDecimal alertThreshold) {
                return usagePercentage.compareTo(alertThreshold.multiply(BigDecimal.valueOf(100))) >= 0;
        }
}
