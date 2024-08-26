package com.revature.LLL.LivestockRecord.converters;

import com.revature.LLL.LivestockRecord.TreatmentPlan;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

@Converter(autoApply = true)
public class TreatmentPlanConverter implements AttributeConverter<TreatmentPlan, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    // takes in CurrentCondition instance and serializes it into a json string to store in database
    public String convertToDatabaseColumn(TreatmentPlan treatmentPlan) {
        if (treatmentPlan == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(treatmentPlan);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting TreatmentPlan to JSON string", e);
        }
    }

    @Override
    public TreatmentPlan convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, TreatmentPlan.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON string to TreatmentPlan object", e);
        }
    }
}