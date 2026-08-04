package com.universidad.proyecto.findtrack.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpensesByCategoryResponseDTO {
    private UUID categoryId;

    private String categoryName;

    private String categoryIcon;

    private BigDecimal totalAmount;

    private BigDecimal percentage;

    private Long transactionCount;
}
