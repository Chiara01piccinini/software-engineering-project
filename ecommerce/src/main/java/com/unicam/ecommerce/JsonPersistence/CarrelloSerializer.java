package com.unicam.ecommerce.JsonPersistence;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.unicam.ecommerce.Component.Carrello;
import com.unicam.ecommerce.Component.IElemento;

import java.io.IOException;
import java.util.Map;

public class CarrelloSerializer extends JsonSerializer<Carrello> {
    @Override
    public void serialize(Carrello carrello, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray(); // scriviamo come lista
        for (Map.Entry<IElemento, Integer> entry : carrello.getProdotti().entrySet()) {
            IElemento elemento = entry.getKey();
            gen.writeStartObject();
            gen.writeStringField("id", elemento.getId().toString());
            gen.writeStringField("nome", elemento.getNome());
            gen.writeNumberField("quantita", entry.getValue());
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }
}
