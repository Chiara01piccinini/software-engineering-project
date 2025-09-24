package com.unicam.ecommerce.Component;

import com.unicam.ecommerce.Service.Gestore;
import com.unicam.ecommerce.Service.Mediator;

/**
 * Classe che rappresenta un Trasformatore nel marketplace.
 * Estende Venditore, quindi eredita proprietà come nome, cognome e azienda.
 * I trasformatori possono richiedere l'aggiunta di informazioni sui prodotti trasformati.
 */
public class Trasformatore extends Venditore {

    /**
     * Costruttore per creare un Trasformatore associato a una specifica azienda.
     * @param nome Nome del trasformatore
     * @param cognome Cognome del trasformatore
     * @param azienda Azienda associata al trasformatore
     */
    public Trasformatore(String nome, String cognome, Azienda azienda) {
        super(nome, cognome, azienda);
    }

    /**
     * Permette al trasformatore di richiedere l'aggiunta di informazioni testuali
     * su un prodotto trasformato. La richiesta viene inviata al Gestore che gestisce
     * le approvazioni.
     *
     * @param prodotto Prodotto trasformato su cui aggiungere le informazioni
     * @param titolo Titolo dell'informazione
     * @param descrizione Descrizione dettagliata dell'informazione
     * @param gestore Gestore incaricato di processare la richiesta
     */
    public void richiediAggiuntaInformazioni(ProdottoTrasformato prodotto,
                                             String titolo,
                                             String descrizione,
                                             Mediator gestore) {
        // Creazione dell'oggetto InformazioniTestuali
        InformazioniTestuali info = new InformazioniTestuali(titolo, descrizione);

        // Creazione della richiesta da inviare al curatore tramite il gestore
        RichiestaInformazioni richiesta = new RichiestaInformazioni(titolo, this, prodotto.getNome(), info);

        // Invio della richiesta al gestore
        gestore.gestisciRichiestaInformazioni(richiesta, this);

        // Log di conferma per il trasformatore
        System.out.println("[Trasformatore] Richiesta informazioni inviata per il prodotto: " + prodotto.getNome());
    }
}
