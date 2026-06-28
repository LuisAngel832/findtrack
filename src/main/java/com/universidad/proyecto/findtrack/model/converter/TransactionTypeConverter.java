package com.universidad.proyecto.findtrack.model.converter;

import com.universidad.proyecto.findtrack.model.TransactionType;

import jakarta.persistence.AttributeConverter;

public class TransactionTypeConverter implements AttributeConverter<TransactionType, String> {

    @Override
    public String convertToDatabaseColumn(TransactionType transactionType) {
        if (transactionType == null) {
            return null;
        }
        return transactionType.name().toLowerCase();
    }

    @Override
    public TransactionType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return TransactionType.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid value for TransactionType: " + dbData);
        }
    }
    
}
