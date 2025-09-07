package org.example.software_engineering_project.model.JsonPersistence;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.software_engineering_project.model.*;

import java.util.Map;
import java.util.UUID;

public class MarketplaceData {

    private Map<UUID, Prodotto> prodotti;
    private Map<UUID, Pacchetto> pacchetti;
    private Map<UUID, Evento> eventi;
    private Map<UUID, Account> profili;

    // Costruttore vuoto richiesto da Jackson
    public MarketplaceData() {}

    @JsonCreator
    public MarketplaceData(
            @JsonProperty("prodotti") Map<UUID, Prodotto> prodotti,
            @JsonProperty("pacchetti") Map<UUID, Pacchetto> pacchetti,
            @JsonProperty("eventi") Map<UUID, Evento> eventi,
            @JsonProperty("profili") Map<UUID, Account> profili
    ) {
        this.prodotti = prodotti;
        this.pacchetti = pacchetti;
        this.eventi = eventi;
        this.profili = profili;
    }

    public Map<UUID, Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(Map<UUID, Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public Map<UUID, Pacchetto> getPacchetti() {
        return pacchetti;
    }

    public void setPacchetti(Map<UUID, Pacchetto> pacchetti) {
        this.pacchetti = pacchetti;
    }

    public Map<UUID, Evento> getEventi() {
        return eventi;
    }

    public void setEventi(Map<UUID, Evento> eventi) {
        this.eventi = eventi;
    }

    public Map<UUID, Account> getProfili() {
        return profili;
    }

    public void setProfili(Map<UUID, Account> profili) {
        this.profili = profili;
    }
}

