        package com.universidad.proyecto.findtrack.dto.response;

        import java.math.BigDecimal;
        import java.time.LocalDate;

        import lombok.AllArgsConstructor;
        import lombok.Getter;

        @Getter
        @AllArgsConstructor
        public class DailyTimelineResponseDTO {
                private final LocalDate date;
                private final BigDecimal totalExpenses;
                private final BigDecimal totalIncome;
        }
