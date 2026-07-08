package com.universidad.proyecto.findtrack.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class BudgetResponseDTO {
   
    private UUID id;

    private UUID categoryId;

    private BigDecimal limitAmount;

    private Integer month;

    private Integer year;

    private BigDecimal alertThreshold;

    private BigDecimal spentAmount;

    private BigDecimal usagePercentage;

    private boolean alertTriggered;

}
