package com.universidad.proyecto.findtrack.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlySummaryDTO {
    private Integer month;

    private Integer year;

    private BigDecimal totalIncome;

    private BigDecimal totalExpenses;

    private BigDecimal netBalance;

    private Long transactionCount;
    
}
