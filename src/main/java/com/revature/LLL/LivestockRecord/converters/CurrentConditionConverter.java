package com.revature.LLL.LivestockRecord.converters;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.LLL.LivestockRecord.CurrentCondition;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

@Converter(autoApply = true)
public class CurrentConditionConverter implements AttributeConverter<CurrentCondition, String> {
    private final ObjectMapper objectMapper;

    public CurrentConditionConverter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    // takes in CurrentCondition instance and serializes it into a json string to store in database
    public String convertToDatabaseColumn(CurrentCondition condition) {
        // if LivestockRecord object doesnt have condition yet, return null
        if(condition == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(condition);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting CurrentCondition to JSON string", e);
        }
    }

    @Override
    public CurrentCondition convertToEntityAttribute(String dbData) {
        // if entry from livestock table doesnt have condition yet, return null
        if(dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, CurrentCondition.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON string to CurrentCondition object", e);
        }
    }
}