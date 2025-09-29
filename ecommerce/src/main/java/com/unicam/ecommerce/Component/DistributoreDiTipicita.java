package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicam.ecommerce.Service.Gestore;
import com.unicam.ecommerce.Service.Mediator;

import java.math.BigDecimal;
import java.util.Map;

/**
 * La classe DistributoreDiTipicita rappresenta un tipo specifico di Venditore.
 *
 * Oltre alle funzionalità di base di un venditore (eredita da {@link Venditore}),
 * questo ruolo ha la possibilità di:
 *   - richiedere l'aggiunta di pacchetti di prodotti;
 *   - richiedere l'aggiunta di informazioni testuali relative ai pacchetti.
 */
public class DistributoreDiTipicita extends Venditore {

    // ==============================
    // COSTRUTTORE
    // ==============================

    /**
     * Crea un nuovo distributore di tipicità.
     *
     * @param nome     Nome del venditore
     * @param cognome  Cognome del venditore
     * @param azienda  Azienda associata
     */
    @JsonCreator
    public DistributoreDiTipicita(@JsonProperty("tipo") TipoAccount tipo,@JsonProperty("nome") String nome,@JsonProperty("cognome") String cognome,@JsonProperty("azienda") Azienda azienda) {
        super(tipo,nome, cognome, azienda);
    }

    // ==============================
    // RICHIESTE AL GESTORE
    // ==============================

    /**
     * Invia al Gestore una richiesta di aggiunta pacchetto.
     *
     * @param nomePacchetto      Nome del pacchetto
     * @param quantita           Quantità totale del pacchetto
     * @param prodotti           Mappa dei prodotti inclusi (id → Prodotto)
     * @param percentualeSconto  Percentuale di sconto applicata al pacchetto
     * @param gestore            Gestore a cui inviare la richiesta
     */
    public void richiediAggiuntaPacchetto(String nomePacchetto,
                                          int quantita,
                                          Map<Integer, Prodotto> prodotti,
                                          BigDecimal percentualeSconto,
                                          Mediator gestore) {
        RichiestaPacchetto richiesta = new RichiestaPacchetto(
                nomePacchetto,
                prodotti,
                percentualeSconto,
                quantita,
                this
        );
        gestore.gestisciRichiestaPacchetto(richiesta, this);
    }

    /**
     * Invia al Gestore una richiesta di aggiunta informazioni testuali su un pacchetto.
     *
     * @param pacchetto    Pacchetto a cui associare le informazioni
     * @param titolo       Titolo delle informazioni
     * @param descrizione  Descrizione testuale
     * @param gestore      Gestore a cui inviare la richiesta
     */
    public void richiediAggiuntaInformazioni(Pacchetto pacchetto,
                                             String titolo,
                                             String descrizione,
                                             Mediator gestore) {
        // Creazione dell'oggetto informazioni testuali
        InformazioniTestuali info = new InformazioniTestuali(titolo, descrizione);

        // Creazione della richiesta da inviare al Gestore
        RichiestaInformazioni richiesta = new RichiestaInformazioni(
                titolo,
                this,
                pacchetto.getNome(),
                info
        );

        // Invio al Gestore
        gestore.gestisciRichiestaInformazioni(richiesta, this);

        System.out.println("[Distributore] Richiesta informazioni inviata per il pacchetto: " + pacchetto.getNome());
    }
}
