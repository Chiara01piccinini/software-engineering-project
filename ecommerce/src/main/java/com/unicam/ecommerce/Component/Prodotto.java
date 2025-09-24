package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.math.BigDecimal;
import java.util.UUID;

public class Prodotto implements IElemento {
    private final UUID id;                  // Identificativo univoco del prodotto
    protected String nome;                  // Nome del prodotto
    protected Azienda azienda;              // Azienda che produce o fornisce il prodotto
    private Boolean vendita = false;        // Flag che indica se il prodotto è disponibile alla vendita
    private InformazioniTestuali descrizione; // Eventuali informazioni testuali aggiuntive sul prodotto
    private BigDecimal prezzo;              // Prezzo unitario del prodotto
    protected int quantita;                 // Quantità disponibile del prodotto

    /**
     * Costruttore principale per creare un prodotto.
     * L'id viene generato automaticamente, il prezzo inizialmente è zero.
     * @param nome Nome del prodotto
     * @param azienda Azienda produttrice
     * @param quantita Quantità disponibile
     */
    @JsonCreator
    public Prodotto(
            @JsonProperty("nome") String nome,
            @JsonProperty("azienda") Azienda azienda,
            @JsonProperty("quantità") int quantita
    ) {
        this.id = UUID.randomUUID();  // Genera un ID univoco per il prodotto
        this.nome = nome;
        this.azienda = azienda;
        this.prezzo = BigDecimal.ZERO;
        this.quantita = quantita;
    }

    @Override
    @JsonValue
    public String toString() {
        return nome;  // Rappresentazione testuale del prodotto: solo il nome
    }

    // -------------------------------
    // Getter e Setter
    // -------------------------------
    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }

    public Boolean getVendita() {
        return vendita;
    }

    public void setVendita(Boolean vendita) {
        this.vendita = vendita;
    }

    public InformazioniTestuali getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(InformazioniTestuali descrizione) {
        this.descrizione = descrizione;
    }

    public BigDecimal calcolaPrezzo() {
        return prezzo;  // Restituisce il prezzo del prodotto
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
