package com.unicam.ecommerce.Component;

import com.unicam.ecommerce.Service.MappaService;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Classe che rappresenta il Marketplace dell'e-commerce.
 *
 * Funzioni principali:
 * - Gestione dei prodotti, pacchetti ed eventi disponibili
 * - Gestione degli account degli utenti
 * - Integrazione con il servizio Mappa per la gestione dei luoghi
 *
 * Tutte le entità (Prodotto, Pacchetto, Evento, Account) sono identificate da UUID.
 */
@Component
public class Marketplace {

    // -------------------------------
    // Collezioni principali del marketplace
    // -------------------------------
    private Map<UUID, Prodotto> prodotti = new LinkedHashMap<>();   // Prodotti disponibili
    private Map<UUID, Pacchetto> pacchetti = new LinkedHashMap<>(); // Pacchetti disponibili
    private Map<UUID, Evento> eventi = new LinkedHashMap<>();       // Eventi disponibili
    private Map<UUID, Account> profili = new LinkedHashMap<>();     // Account registrati

    private MappaService mappa = new MappaService();               // Servizio per gestione luoghi

    // -------------------------------
    // Getter e setter
    // -------------------------------

    public MappaService getMappa() {
        return mappa;
    }

    public void setMappa(MappaService mappa) {
        this.mappa = mappa;
    }

    // -------------------------------
    // Metodi per la gestione dei Prodotti
    // -------------------------------
    public void stampaProdotti() {
        for (Prodotto p : prodotti.values()) {
            System.out.println(p.getNome());
        }
    }

    public Map<UUID, Prodotto> getProdotti() { return prodotti; }

    public void setProdotti(Map<UUID, Prodotto> prodotti) { this.prodotti = prodotti; }

    public void aggiungiProdotto(Prodotto p) { prodotti.putIfAbsent(p.getId(), p); }

    public void rimuoviProdotto(UUID id) { prodotti.remove(id); }

    // -------------------------------
    // Metodi per la gestione dei Pacchetti
    // -------------------------------
    public void stampaPacchetti() {
        for (Pacchetto p : pacchetti.values()) {
            System.out.println(p.getNome());
        }
    }

    public Map<UUID, Pacchetto> getPacchetti() { return pacchetti; }

    public void setPacchetti(Map<UUID, Pacchetto> pacchetti) { this.pacchetti = pacchetti; }

    public void aggiungiPacchetto(Pacchetto pacchetto) { pacchetti.putIfAbsent(pacchetto.getId(), pacchetto); }

    public void rimuoviPacchetto(UUID id) { pacchetti.remove(id); }

    // -------------------------------
    // Metodi per la gestione degli Eventi
    // -------------------------------
    public void stampaEventi() {
        for (Evento e : eventi.values()) {
            System.out.println(e.getNome());
        }
    }

    public Map<UUID, Evento> getEventi() { return eventi; }

    public void setEventi(Map<UUID, Evento> eventi) { this.eventi = eventi; }

    public void aggiungiEvento(Evento evento) { eventi.putIfAbsent(evento.getId(), evento); }

    // -------------------------------
    // Metodi per la gestione degli Account
    // -------------------------------
    public void stampaAccount() {
        for (Account a : profili.values()) {
            System.out.println(a.getNomeUtente());
        }
    }

    public Map<UUID, Account> getProfili() { return profili; }

    public void setAccount(Map<UUID, Account> profili) { this.profili = profili; }

    public void aggiungiAccount(Account account) {
        profili.putIfAbsent(account.getId(), account);
    }

    // -------------------------------
    // Metodi utili di ricerca
    // -------------------------------

    /**
     * Restituisce l'account in base al nome utente.
     */
    public Account getAccountByUsername(String username) {
        return profili.values().stream()
                .filter(a -> a.getNomeUtente().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Restituisce un elemento (Prodotto o Pacchetto) in base all'ID.
     */
    public IElemento getElementoById(UUID id) {
        if (prodotti.containsKey(id)) return prodotti.get(id);
        if (pacchetti.containsKey(id)) return pacchetti.get(id);
        return null;
    }

    /**
     * Restituisce un Evento in base all'ID.
     */
    public Evento getEventoById(UUID id) {
        if (eventi.containsKey(id)) return eventi.get(id);
        return null;
    }
}
