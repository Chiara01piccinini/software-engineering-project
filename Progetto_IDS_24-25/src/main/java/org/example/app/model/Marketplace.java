package org.example.app.model;

import java.util.*;

public class Marketplace {

    // Uso LinkedHashMap per preservare l'ordine di inserimento
    private static Map<UUID, Prodotto> prodotti = new LinkedHashMap<>();
    private static Map<UUID, Pacchetto> pacchetti = new LinkedHashMap<>();
    private static Map <UUID,Evento> eventi = new LinkedHashMap<>();
    private static Map <UUID,Account> profili = new LinkedHashMap<>();

    public static Map<UUID, Account> getAccount() {
        return profili;
    }

    public static void setAccount(Map<UUID, Account> account) {
        profili = account;
    }

    public static Map<UUID, Evento> getEventi() {
        return eventi;
    }

    public static void setEventi(Map<UUID, Evento> nuoviEventi) {
        eventi = nuoviEventi;
    }


    public static Map<UUID, Prodotto> getProdotti() {
        return prodotti;
    }

    public static void setProdotti(Map<UUID, Prodotto> nuoviProdotti) {
        prodotti = nuoviProdotti;
    }

    public static Prodotto getProdottoById(UUID id) {
        return prodotti.get(id);
    }

    public static void aggiungiProdotto(Prodotto p) {
        if (!prodotti.containsKey(p.getId())) {
            prodotti.put(p.getId(), p);
            System.out.println(" Prodotto pubblicato: " + p.getNome());
        } else {
            System.out.println(" Prodotto già presente: " + p.getNome());
        }
    }
    public static void rimuoviProdotto(UUID id) {
        Prodotto rimosso = prodotti.remove(id);
        if (rimosso == null) {
            throw new NoSuchElementException("Nessun prodotto trovato con ID: " + id);
        }
    }

    public static IElemento getElementoById(UUID id) {
        if (prodotti.containsKey(id)) {
            return prodotti.get(id);
        } else if (pacchetti.containsKey(id)) {
            return pacchetti.get(id);
        } else {
            return null;
        }
    }

    public static Map<UUID, Pacchetto> getPacchetti() {
        return pacchetti;
    }

    public static void setPacchetti(Map<UUID, Pacchetto> nuoviPacchetti) {
        pacchetti = nuoviPacchetti;
    }

    public static Pacchetto getPacchettoById(UUID id) {
        return pacchetti.get(id);
    }

    public static void aggiungiPacchetto(Pacchetto pacchetto) {
        if (!pacchetti.containsKey(pacchetto.getId())) {
            pacchetti.put(pacchetto.getId(), pacchetto);
            System.out.println(" Pacchetto pubblicato: " + pacchetto.getNome());
        } else {
            System.out.println(" Pacchetto già presente: " + pacchetto.getNome());
        }

    }
    public static void rimuoviPacchetto(UUID id) {
        Pacchetto rimosso = pacchetti.remove(id);
        if (rimosso == null) {
            throw new NoSuchElementException("Nessun pacchetto trovato con ID: " + id);
        }
    }
    public static void aggiungiEvento(Evento evento){
        if (!eventi.containsKey(evento.getID())) {
            eventi.put(evento.getID(), evento);
            System.out.println("Evento pubblicato: " + evento.getNome());
        } else {
            System.out.println(" Evento già presente: " + evento.getNome());
        }
    }
    public static void aggiungiAccount(Account account){
        if (!profili.containsKey(account.getId())) {
            profili.put(account.getId(), account);
            System.out.println("Account pubblicato: " + account.getNomeUtente());
        } else {
            System.out.println("Account già presente: " + account.getNomeUtente());
        }
    }

    public Account getAccountByUsername(String username) {
        for (Account account : profili.values()) {
            if (account.getNomeUtente().equals(username)) {
                return account;
            }
        }
        return null; // account non trovato
    }
}
