package com.unicam.ecommerce.Component;

import com.unicam.ecommerce.Service.Gestore;

/**
 * La classe Produttore estende Venditore e rappresenta un utente
 * che può richiedere l'aggiunta di informazioni dettagliate sui prodotti.
 */
public class Produttore extends Venditore {

    /**
     * Costruttore per creare un Produttore associato a un'azienda.
     *
     * @param nome     Nome del produttore
     * @param cognome  Cognome del produttore
     * @param azienda  Azienda di appartenenza del produttore
     */
    public Produttore(String nome, String cognome, Azienda azienda) {
        super(nome, cognome, azienda);
    }

    /**
     * Richiede l'aggiunta di informazioni testuali per un prodotto.
     * Crea un oggetto RichiestaInformazioni e lo invia al curatore tramite il Gestore.
     *
     * @param prodotto   Il prodotto su cui aggiungere informazioni
     * @param titolo     Titolo della sezione informativa
     * @param descrizione Descrizione dettagliata
     * @param gestore    Istanza del Gestore che gestisce la richiesta
     */
    public void richiediAggiuntaInformazioni(Prodotto prodotto, String titolo, String descrizione, Gestore gestore) {
        InformazioniTestuali info = new InformazioniTestuali(titolo, descrizione);
        RichiestaInformazioni richiesta = new RichiestaInformazioni(titolo, this, prodotto.getNome(), info);
        gestore.gestisciRichiestaInformazioni(richiesta, this);
    }
}
