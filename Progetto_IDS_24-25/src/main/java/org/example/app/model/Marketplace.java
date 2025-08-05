package org.example.app.model;

import org.example.app.controls.IGestore;

import java.util.*;

public class Marketplace {
    private IGestore gestore;

    // Uso LinkedHashMap per preservare l'ordine di inserimento
    private static Map<UUID, Prodotto> prodotti = new LinkedHashMap<>();
    private static Map<UUID, Pacchetto> pacchetti = new LinkedHashMap<>();

    public Marketplace(IGestore gestore) {
        this.gestore = gestore;
    }

    public IGestore getGestore() {
        return gestore;
    }

    public void setGestore(IGestore gestore) {
        this.gestore = gestore;
    }

    // GETTER prodotti
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

    // GETTER pacchetti
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
}
