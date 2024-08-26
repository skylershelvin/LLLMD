package com.revature.LLL.LivestockRecord.converters;

import com.revature.LLL.LivestockRecord.AdditionalNotes;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

@Converter(autoApply = true)
public class AdditionalNotesConverter implements AttributeConverter<AdditionalNotes, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    // takes in CurrentCondition instance and serializes it into a json string to store in database
    public String convertToDatabaseColumn(AdditionalNotes additionalNotes) {
        if(additionalNotes == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(additionalNotes);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting AdditionalNotes to JSON string", e);
        }
    }

    @Override
    public AdditionalNotes convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, AdditionalNotes.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON string to AdditionalNotes object", e);
        }
    }
}