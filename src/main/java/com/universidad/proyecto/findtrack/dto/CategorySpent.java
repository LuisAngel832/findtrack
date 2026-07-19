package com.universidad.proyecto.findtrack.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CategorySpent(UUID categoryId, BigDecimal spent) {
    
}
