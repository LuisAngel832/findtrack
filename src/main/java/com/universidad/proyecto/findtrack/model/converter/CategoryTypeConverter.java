package com.universidad.proyecto.findtrack.model.converter;
import com.universidad.proyecto.findtrack.model.CategoryType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryTypeConverter implements AttributeConverter<CategoryType, String> {

    @Override
    public String convertToDatabaseColumn(CategoryType categoryType) {
        if (categoryType == null) {
            return null;
        }
        return categoryType.name().toLowerCase();
    }

    @Override
    public CategoryType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return CategoryType.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid value for CategoryType: " + dbData);
        }
    }
    
}
