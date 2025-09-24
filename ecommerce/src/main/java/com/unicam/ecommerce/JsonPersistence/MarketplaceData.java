package com.unicam.ecommerce.JsonPersistence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicam.ecommerce.Component.Account;
import com.unicam.ecommerce.Component.Evento;
import com.unicam.ecommerce.Component.Pacchetto;
import com.unicam.ecommerce.Component.Prodotto;

import java.util.Map;
import java.util.UUID;

/**
 * Classe di supporto per la persistenza in JSON del marketplace.
 * Rappresenta lo stato completo del marketplace in un singolo oggetto,
 * includendo prodotti, pacchetti, eventi e account degli utenti.
 *
 * Viene utilizzata per:
 * - Salvare lo stato del marketplace su file JSON.
 * - Caricare lo stato del marketplace da un file JSON.
 *
 * Contiene:
 * - Mappe UUID -> oggetto per ogni tipo di entità gestita (Prodotto, Pacchetto, Evento, Account).
 *
 * Jackson usa il costruttore annotato con @JsonCreator per deserializzare correttamente
 * i dati dal JSON e popolare le mappe interne.
 */
public class MarketplaceData {

    private Map<UUID, Prodotto> prodotti;   // Tutti i prodotti del marketplace
    private Map<UUID, Pacchetto> pacchetti; // Tutti i pacchetti del marketplace
    private Map<UUID, Evento> eventi;       // Tutti gli eventi disponibili
    private Map<UUID, Account> profili;     // Tutti gli account registrati

    // Costruttore vuoto richiesto da Jackson per la deserializzazione
    public MarketplaceData() {}

    /**
     * Costruttore principale usato da Jackson per creare l'oggetto da JSON.
     */
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

    // Getter e setter per tutte le mappe
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
