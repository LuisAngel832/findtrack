package com.universidad.proyecto.findtrack.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.proyecto.findtrack.dto.response.MonthlySummaryDTO;
import com.universidad.proyecto.findtrack.model.TransactionType;
import com.universidad.proyecto.findtrack.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {
    private final TransactionRepository transactionRepository;

    public MonthlySummaryDTO getMonthlySummary(UUID userId, Integer year, Integer month) {
        LocalDate inicioMes = LocalDate.of(year, month, 1);
        LocalDate inicioMesSiguiente = inicioMes.plusMonths(1);

        BigDecimal totalIncome = transactionRepository.sumAmountByUserAndTypeAndDate(userId, TransactionType.INCOME, inicioMes, inicioMesSiguiente);
        BigDecimal totalExpenses = transactionRepository.sumAmountByUserAndTypeAndDate(userId, TransactionType.EXPENSE, inicioMes, inicioMesSiguiente);
        Long transactionCount = transactionRepository.countTransactionsByUserAndDate(userId, inicioMes, inicioMesSiguiente);

        totalIncome = totalIncome.setScale(2, RoundingMode.HALF_UP);
        totalExpenses = totalExpenses.setScale(2, RoundingMode.HALF_UP);

        BigDecimal netBalance = totalIncome.subtract(totalExpenses).setScale(2, RoundingMode.HALF_UP);

        return new MonthlySummaryDTO(month, year, totalIncome, totalExpenses, netBalance, transactionCount);
    }

    
}
