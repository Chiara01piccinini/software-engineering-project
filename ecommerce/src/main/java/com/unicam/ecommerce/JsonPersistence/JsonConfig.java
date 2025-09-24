package com.unicam.ecommerce.JsonPersistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Configurazione centralizzata per Jackson ObjectMapper.
 * Responsabilità:
 * - Serializzare e deserializzare oggetti Java in JSON.
 */
public class JsonConfig {

    private static final ObjectMapper mapper = createMapper();

    private static ObjectMapper createMapper() {
        ObjectMapper objectMapper = new ObjectMapper();


        objectMapper.registerModule(new JavaTimeModule());

        // Serializza le date come stringhe leggibili, non come timestamp numerici
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Ignora valori null per pulizia del JSON
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper;
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
