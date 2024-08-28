package com.revature.LLL.LivestockRecord.converters;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.LLL.LivestockRecord.VetRecord;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

@Converter(autoApply = true)
public class VetRecordConverter implements AttributeConverter<VetRecord, String> {
    private final ObjectMapper objectMapper;

    public VetRecordConverter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    // takes in CurrentCondition instance and serializes it into a json string to store in database
    public String convertToDatabaseColumn(VetRecord vetRecord) {
        if(vetRecord == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(vetRecord);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting VetRecord to JSON string", e);
        }
    }

    @Override
    public VetRecord convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, VetRecord.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON string to VetRecord object", e);
        }
    }
}