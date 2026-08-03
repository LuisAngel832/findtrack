package com.universidad.proyecto.findtrack.common;

import java.time.LocalDate;
import java.time.YearMonth;

public final class DateRangeUtil {

    private DateRangeUtil() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Clase de utilidad - no instanciar");
    }
    
    public static DateRange getDateRange(YearMonth yearMonth) {
        LocalDate monthStart = yearMonth.atDay(1);
        LocalDate nextMonthStart = monthStart.plusMonths(1);
        return new DateRange(monthStart, nextMonthStart);
    }
}
