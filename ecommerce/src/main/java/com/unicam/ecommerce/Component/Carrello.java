package com.unicam.ecommerce.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * La classe Carrello rappresenta il carrello della spesa di un utente.
 * Contiene una collezione di prodotti (IElemento) e la relativa quantità.
 */
public class Carrello {
    /**
     * Mappa che associa ad ogni elemento (prodotto/evento) la quantità selezionata dall'utente.
     * - La chiave è un oggetto che implementa l'interfaccia IElemento.
     * - Il valore è la quantità di quell'elemento nel carrello.
     */
    private final Map<IElemento, Integer> prodotti = new HashMap<>();

    /**
     * Aggiunge un prodotto al carrello o aggiorna la quantità se già presente.
     * @param p prodotto da aggiungere
     * @param quantita quantità da aggiungere
     */
    public void aggiungiProdotto(IElemento p, int quantita) {
        if (p == null || quantita <= 0) return; // Controllo parametri non validi
        // Se il prodotto esiste già, incrementa la quantità, altrimenti lo inserisce
        prodotti.merge(p, quantita, Integer::sum);
    }

    /**
     * Rimuove un prodotto dal carrello.
     * @param p prodotto da rimuovere
     * @return true se l'operazione è riuscita
     */
    public boolean rimuoviProdotto(IElemento p) {
        prodotti.remove(p);
        return true; // Sempre true, ma si potrebbe migliorare restituendo false se non esiste
    }

    /**
     * Restituisce la mappa completa dei prodotti con le quantità.
     * @return mappa prodotti-quantità
     */
    public Map<IElemento, Integer> getProdotti() {
        return prodotti;
    }

    /**
     * Svuota completamente il carrello.
     */
    public void svuota() {
        prodotti.clear();
    }

    /**
     * Aggiunge un elemento al carrello con la quantità specificata,
     * senza sommare a quella già presente (sovrascrive).
     * @param elemento elemento da aggiungere
     * @param quantita quantità da associare
     * @return true se l'elemento è stato aggiunto, false se parametri non validi
     */
    public Boolean aggiungiElemento(IElemento elemento, Integer quantita) {
        if (elemento != null && quantita > 0 ) {
            prodotti.put(elemento, quantita); // qui sovrascrive la quantità
            return true;
        }
        return false;
    }
}
