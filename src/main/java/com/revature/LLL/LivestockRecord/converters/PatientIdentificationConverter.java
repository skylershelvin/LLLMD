package com.revature.LLL.LivestockRecord.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.LLL.LivestockRecord.PatientIdentification;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PatientIdentificationConverter implements AttributeConverter<PatientIdentification, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(PatientIdentification patientIdentification) {
        try{
            return objectMapper.writeValueAsString(patientIdentification);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting Livestock to JSON string", e);
        }
    }

    @Override
    public PatientIdentification convertToEntityAttribute(String dbData) {
        try{
            return objectMapper.readValue(dbData, PatientIdentification.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON string to Livestock object", e);
        }
    }
}
