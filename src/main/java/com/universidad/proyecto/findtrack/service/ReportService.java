package com.universidad.proyecto.findtrack.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.proyecto.findtrack.common.DateRange;
import com.universidad.proyecto.findtrack.dto.response.ExpensesByCategoryResponseDTO;
import com.universidad.proyecto.findtrack.dto.response.MonthlySummaryDTO;
import com.universidad.proyecto.findtrack.model.TransactionType;
import com.universidad.proyecto.findtrack.projection.CategorySpentSummary;
import com.universidad.proyecto.findtrack.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {
        private final TransactionRepository transactionRepository;

        private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

        public MonthlySummaryDTO getMonthlySummary(UUID userId, Integer year, Integer month) {
                LocalDate inicioMes = LocalDate.of(year, month, 1);
                LocalDate inicioMesSiguiente = inicioMes.plusMonths(1);

                BigDecimal totalIncome = transactionRepository.sumAmountByUserAndTypeAndDate(userId,
                                TransactionType.INCOME,
                                inicioMes, inicioMesSiguiente);
                BigDecimal totalExpenses = transactionRepository.sumAmountByUserAndTypeAndDate(userId,
                                TransactionType.EXPENSE,
                                inicioMes, inicioMesSiguiente);
                Long transactionCount = transactionRepository.countTransactionsByUserAndDate(userId, inicioMes,
                                inicioMesSiguiente);

                totalIncome = totalIncome.setScale(2, RoundingMode.HALF_UP);
                totalExpenses = totalExpenses.setScale(2, RoundingMode.HALF_UP);

                BigDecimal netBalance = totalIncome.subtract(totalExpenses).setScale(2, RoundingMode.HALF_UP);

                return new MonthlySummaryDTO(month, year, totalIncome, totalExpenses, netBalance, transactionCount);
        }

        private record CategoryPercentage(CategorySpentSummary category, BigDecimal percentage) {
        }

        public List<ExpensesByCategoryResponseDTO> expensesByCategory(UUID userId, DateRange dateRange,
                        TransactionType type) {

                List<CategorySpentSummary> categorySpentSummaries = transactionRepository
                                .findCategorySpentSummaryByMonth(userId, dateRange.monthStart(),
                                                dateRange.nextMonthStart(), type);

                if (categorySpentSummaries.isEmpty()) {
                        return List.of();
                }

                BigDecimal grandTotal = categorySpentSummaries.stream()
                                .map(CategorySpentSummary::totalSpent)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                List<CategoryPercentage> categoryPercentages = categorySpentSummaries.stream()
                                .map(summary -> {
                                        BigDecimal percentage = summary.totalSpent()
                                                        .divide(grandTotal, 4, RoundingMode.HALF_UP)
                                                        .multiply(HUNDRED)
                                                        .setScale(2, RoundingMode.HALF_UP);

                                        return new CategoryPercentage(summary, percentage);
                                })
                                .collect(Collectors.toCollection(ArrayList::new));

                BigDecimal totalPercentage = categoryPercentages.stream()
                                .map(CategoryPercentage::percentage)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal roundingDifference = HUNDRED.subtract(totalPercentage);

                categoryPercentages.set(0, new CategoryPercentage(categoryPercentages.get(0).category(),
                                categoryPercentages.get(0).percentage().add(roundingDifference)));

                return categoryPercentages.stream()
                                .map(cp -> new ExpensesByCategoryResponseDTO(
                                                cp.category().categoryId(),
                                                cp.category().categoryName(),
                                                cp.category().categoryIcon(),
                                                cp.category().totalSpent(),
                                                cp.percentage(),
                                                cp.category().transactionCount()))
                                .toList();

        }

}
