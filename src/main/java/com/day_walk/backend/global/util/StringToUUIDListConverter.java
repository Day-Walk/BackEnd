package com.day_walk.backend.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;
import java.util.UUID;

@Converter
public class StringToUUIDListConverter implements AttributeConverter<List<UUID>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<UUID> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize List<UUID> to JSON", e);
        }
    }

    @Override
    public List<UUID> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, mapper.getTypeFactory().constructCollectionType(List.class, UUID.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not deserialize JSON to List<UUID>", e);
        }
    }
}
