package com.universidad.proyecto.findtrack.model;



import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.universidad.proyecto.findtrack.model.converter.TransactionTypeConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
 

@Entity
@Builder
@AllArgsConstructor (access = AccessLevel.PACKAGE)
@Table(name = "transactions")
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    private UUID userId;

    @NonNull
    private UUID categoryId;

    @NonNull
    private BigDecimal amount;

    
    @Convert(converter = TransactionTypeConverter.class)
    @NonNull
    private TransactionType type;

    private String description;

    @NonNull
    private LocalDate date;

    @CreationTimestamp
    private Instant createdAt;
    
}
