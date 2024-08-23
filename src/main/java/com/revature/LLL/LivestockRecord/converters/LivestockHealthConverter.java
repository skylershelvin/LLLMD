package com.revature.LLL.LivestockRecord.converters;

import com.revature.LLL.LivestockRecord.LivestockHealth;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

@Converter(autoApply = true)
public class LivestockHealthConverter implements AttributeConverter<LivestockHealth, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    // takes in CurrentCondition instance and serializes it into a json string to store in database
    public String convertToDatabaseColumn(LivestockHealth livestockHealth) {
        if(livestockHealth == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(livestockHealth);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting LivestockHealth to JSON string", e);
        }
    }

    @Override
    public LivestockHealth convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, LivestockHealth.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON string to LivestockHealth object", e);
        }
    }
}