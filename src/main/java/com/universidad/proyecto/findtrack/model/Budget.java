package com.universidad.proyecto.findtrack.model;


import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "budgets")
@Builder
@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)

public class Budget {


    public Budget update (BigDecimal limitAmount, BigDecimal alertThreshold) {
        this.limitAmount = limitAmount;
        this.alertThreshold = alertThreshold;
        return this;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    private UUID userId;

    @NonNull
    private UUID categoryId;

    @NonNull
    private BigDecimal limitAmount;

    @NonNull
    private Integer month;

    @NonNull
    private Integer year;

    private BigDecimal alertThreshold;
}
