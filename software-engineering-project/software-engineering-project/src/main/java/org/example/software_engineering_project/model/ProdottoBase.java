package org.example.software_engineering_project.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdottoBase extends Prodotto {
    private final Produttore produttore;

    @JsonCreator
    public ProdottoBase(
            @JsonProperty("nome") String nome,
            @JsonProperty("azienda") Azienda azienda,
            @JsonProperty("produttore") Produttore produttore) {
        super(nome, azienda);
        this.produttore = produttore;
    }

    public Produttore getProduttore() {
        return produttore;
    }
}

