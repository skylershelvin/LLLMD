package com.revature.LLL.LivestockRecord.converters;

import com.revature.LLL.LivestockRecord.MedicalHistory;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

@Converter(autoApply = true)
public class MedicalHistoryConverter implements AttributeConverter<MedicalHistory, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    // takes in CurrentCondition instance and serializes it into a json string to store in database
    public String convertToDatabaseColumn(MedicalHistory medicalHistory) {
        try {
            return objectMapper.writeValueAsString(medicalHistory);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting MedicalHistory to JSON string", e);
        }
    }

    @Override
    public MedicalHistory convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, MedicalHistory.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON string to MedicalHistory object", e);
        }
    }
}