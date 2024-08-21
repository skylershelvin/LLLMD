package com.revature.LLL.LivestockRecord.converters;

import com.revature.LLL.LivestockRecord.CurrentCondition;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

@Converter(autoApply = true)
public class CurrentConditionConverter implements AttributeConverter<CurrentCondition, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    // takes in CurrentCondition instance and serializes it into a json string to store in database
    public String convertToDatabaseColumn(CurrentCondition condition) {
        try {
            return objectMapper.writeValueAsString(condition);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting CurrentCondition to JSON string", e);
        }
    }

    @Override
    public CurrentCondition convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, CurrentCondition.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON string to CurrentCondition object", e);
        }
    }
}