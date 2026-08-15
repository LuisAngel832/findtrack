package com.universidad.proyecto.findtrack.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyTotals(
        LocalDate date,
        BigDecimal totalExpenses,
        BigDecimal totalIncome

) {
}
