package com.universidad.proyecto.findtrack.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BudgetUpdateRequestDTO {

    @NotNull
    @Positive
    private BigDecimal limitAmount;

    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("1.00")
    private BigDecimal alertThreshold;
}
