package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdottoTrasformato extends Prodotto {
    private InformazioniTestuali produzione; // Informazioni aggiuntive specifiche sulla trasformazione del prodotto

    /**
     * Costruttore per creare un prodotto trasformato.
     * Riceve il nome, l'azienda e la quantità disponibile.
     * @param nome Nome del prodotto
     * @param azienda Azienda produttrice
     * @param quantita Quantità disponibile
     */
    @JsonCreator
    public ProdottoTrasformato(
            @JsonProperty("nome") String nome,
            @JsonProperty("azienda") Azienda azienda,
            @JsonProperty("quantita") int quantita
    ) {
        super(nome, azienda, quantita); // Richiama il costruttore della classe base Prodotto
    }

    // -------------------------------
    // Getter e Setter per la produzione
    // -------------------------------
    public InformazioniTestuali getProduzione() {
        return produzione;
    }

    public void setProduzione(InformazioniTestuali produzione) {
        this.produzione = produzione;
    }

    @Override
    public String toString() {
        return this.getNome(); // Rappresentazione testuale: restituisce solo il nome
    }
}
