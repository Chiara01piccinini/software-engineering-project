package org.example.software_engineering_project.model;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Marketplace {

    private Map<UUID, Prodotto> prodotti = new LinkedHashMap<>();
    private Map<UUID, Pacchetto> pacchetti = new LinkedHashMap<>();
    private Map<UUID, Evento> eventi = new LinkedHashMap<>();
    private Map<UUID, Account> profili = new LinkedHashMap<>();

    // Prodotti
    public Map<UUID, Prodotto> getProdotti() { return prodotti; }
    public void setProdotti(Map<UUID, Prodotto> prodotti) { this.prodotti = prodotti; }
    public void aggiungiProdotto(Prodotto p) { prodotti.putIfAbsent(p.getId(), p); }
    public void rimuoviProdotto(UUID id) { prodotti.remove(id); }

    // Pacchetti
    public Map<UUID, Pacchetto> getPacchetti() { return pacchetti; }
    public void setPacchetti(Map<UUID, Pacchetto> pacchetti) { this.pacchetti = pacchetti; }
    public void aggiungiPacchetto(Pacchetto pacchetto) { pacchetti.putIfAbsent(pacchetto.getId(), pacchetto); }
    public void rimuoviPacchetto(UUID id) { pacchetti.remove(id); }

    // Eventi
    public Map<UUID, Evento> getEventi() { return eventi; }
    public void setEventi(Map<UUID, Evento> eventi) { this.eventi = eventi; }
    public void aggiungiEvento(Evento evento) { eventi.putIfAbsent(evento.getID(), evento); }

    // Account
    public Map<UUID, Account> getAccount() { return profili; }
    public void setAccount(Map<UUID, Account> profili) { this.profili = profili; }
    public void aggiungiAccount(Account account) { profili.putIfAbsent(account.getId(), account); }

    // Metodi utili
    public Account getAccountByUsername(String username) {
        return profili.values().stream()
                .filter(a -> a.getNomeUtente().equals(username))
                .findFirst()
                .orElse(null);
    }

    public IElemento getElementoById(UUID id) {
        if (prodotti.containsKey(id)) return prodotti.get(id);
        if (pacchetti.containsKey(id)) return pacchetti.get(id);
        return null;
    }

    public Prodotto getProdottoById(UUID id) { return prodotti.get(id); }
    public Pacchetto getPacchettoById(UUID id) { return pacchetti.get(id); }
}
