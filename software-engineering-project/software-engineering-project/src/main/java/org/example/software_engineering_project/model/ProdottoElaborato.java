package org.example.software_engineering_project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdottoElaborato extends Prodotto {
    private final Trasformatore trasformatore;

    @JsonCreator
    public ProdottoElaborato(
            @JsonProperty("nome") String nome,
            @JsonProperty("azienda") Azienda azienda,
            @JsonProperty("trasformatore") Trasformatore trasformatore) {
        super(nome, azienda);
        this.trasformatore = trasformatore;
    }

    public Trasformatore getTrasformatore() {
        return trasformatore;
    }
}

