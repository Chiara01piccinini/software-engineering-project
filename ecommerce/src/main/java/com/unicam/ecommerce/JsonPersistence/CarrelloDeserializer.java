package com.unicam.ecommerce.JsonPersistence;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.unicam.ecommerce.Component.Carrello;
import com.unicam.ecommerce.Component.IElemento;
import com.unicam.ecommerce.Component.Marketplace;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CarrelloDeserializer extends JsonDeserializer<Carrello> {
    private final Marketplace marketplace;

    public CarrelloDeserializer(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    @Override
    public Carrello deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonProcessingException {
        // Legge la lista di elementi serializzati dal JSON
        List<Map<String, Object>> lista = p.readValueAs(new TypeReference<>() {});
        Carrello carrello = new Carrello();

        for (Map<String, Object> item : lista) {
            UUID id = UUID.fromString((String) item.get("id"));
            Integer quantita = (Integer) item.get("quantita");

            // Recupera l'elemento dal Marketplace tramite id
            IElemento elemento = marketplace.getElementoById(id);
            if (elemento != null && quantita != null) {
                carrello.aggiungiElemento(elemento, quantita);
            }
        }

        return carrello;
    }
}
