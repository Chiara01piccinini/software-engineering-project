package org.example.app.model.JsonPersistence;

import org.example.app.model.*;

import java.util.Map;
import java.util.UUID;

public class MarketplaceData {
    private Map<UUID, Prodotto> prodotti;
    private Map<UUID, Pacchetto> pacchetti;
    private Map <UUID, Evento> eventi;
    private Map <UUID, Account> profili;

    public MarketplaceData(){}

    public MarketplaceData(Map<UUID, Prodotto> prodotti, Map<UUID, Pacchetto> pacchetti, Map <UUID, Evento> eventi, Map <UUID, Account> profili){
        this.prodotti = prodotti;
        this.pacchetti = pacchetti;
        this.eventi = eventi;
        this.profili = profili;
    }

    public Map<UUID, Prodotto> getProdotti() {return prodotti;}
    public Map<UUID, Pacchetto> getPacchetti(){return pacchetti;}
    public Map<UUID, Evento> getEventi() {return eventi;}
    public Map<UUID, Account> getProfili(){return profili;}
}
