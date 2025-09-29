package com.unicam.ecommerce.JsonPersistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.unicam.ecommerce.Component.Carrello;
import com.unicam.ecommerce.Component.Marketplace;

/**
 * Configurazione centralizzata per Jackson ObjectMapper.
 * Responsabilità:
 * - Serializzare e deserializzare oggetti Java in JSON.
 */
public class JsonConfig {

    private static ObjectMapper mapper;

    private static ObjectMapper createMapper(Marketplace marketplace) {
        ObjectMapper objectMapper = new ObjectMapper();


        objectMapper.registerModule(new JavaTimeModule());

        // Serializza le date come stringhe leggibili, non come timestamp numerici
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Ignora valori null per pulizia del JSON
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Modulo per il deserializer custom del Carrello
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Carrello.class, new CarrelloDeserializer(marketplace));
        module.addSerializer(Carrello.class, new CarrelloSerializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }

    public static ObjectMapper getMapper(Marketplace marketplace) {
        if (mapper == null){
            mapper = createMapper(marketplace);
        }
        return mapper;
    }
}
