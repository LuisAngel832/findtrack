package com.universidad.proyecto.findtrack.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class TransactionRequestDTO {
    @NotNull
    private UUID categoryId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    @Pattern(regexp = "^(INCOME|EXPENSE)$", message = "El tipo debe ser INCOME o EXPENSE")
    private String type;

    private String description;

    @NotNull
    @PastOrPresent
    private LocalDate date;

}
