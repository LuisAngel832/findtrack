package com.universidad.proyecto.findtrack.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BudgetRequestDTO {

    @NotNull
    private UUID categoryId;

    @NotNull
    @Positive
    private BigDecimal limitAmount;

    @NotNull
    @Min(value = 1, message = "El mes mínimo permitido es 1")
    @Max(value = 12, message = "El mes máximo permitido es 12")
    private Integer month;

    @NotNull
    @Min(value = 2000, message = "El año mínimo permitido es 2000")
    @Max(value = 2100, message = "El año máximo permitido es 2100")
    private Integer year;

    @DecimalMin("0.01")
    @DecimalMax("1.00")
    private BigDecimal alertThreshold;

}
