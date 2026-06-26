package com.universidad.proyecto.findtrack.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.universidad.proyecto.findtrack.model.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class TransactionResponseDTO {
    
    private UUID id;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private LocalDate date;
    private Instant createdAt;
    private CategoryResponseDTO category;

}
