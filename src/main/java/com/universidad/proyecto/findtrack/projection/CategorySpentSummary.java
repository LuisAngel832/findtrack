package com.universidad.proyecto.findtrack.projection;

import java.math.BigDecimal;
import java.util.UUID;

public record CategorySpentSummary (
    UUID categoryId,
    String categoryName,
    String categoryIcon,
    BigDecimal totalSpent,
    Long transactionCount
){
    
}
