package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicam.ecommerce.Service.Gestore;
import com.unicam.ecommerce.Service.Mediator;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * La classe Animatore rappresenta un venditore particolare che
 * può proporre eventi e aggiungere informazioni testuali ad essi.
 * Estende la classe Venditore, quindi eredita tutte le sue funzionalità.
 */
public class Animatore extends Venditore {

    /**
     * Costruttore dell'animatore.
     * @param nome nome dell'animatore
     * @param cognome cognome dell'animatore
     * @param azienda azienda di appartenenza
     */
    @JsonCreator
    public Animatore(@JsonProperty("tipo") TipoAccount tipo,@JsonProperty("nome")String nome,@JsonProperty("cognome") String cognome,@JsonProperty("azienda") Azienda azienda) {
        super(tipo,nome, cognome, azienda); // chiama il costruttore di Venditore
    }

    /**
     * Richiede l'aggiunta di un nuovo evento al sistema.
     * L'animatore non aggiunge direttamente l'evento, ma invia una richiesta
     * al Gestore, che funge da mediatore e valida la proposta.
     *
     * @param nomeEvento nome dell'evento
     * @param biglietti numero di biglietti disponibili
     * @param data data dell'evento
     * @param orario orario dell'evento
     * @param posizione posizione/luogo in cui si tiene l'evento
     * @param gestore riferimento al Gestore che processa la richiesta
     */
    public void richiediAggiuntaEvento(String nomeEvento, int biglietti, LocalDate data, LocalTime orario, Posizione posizione, Mediator gestore) {
        RichiestaEvento richiesta = new RichiestaEvento(nomeEvento, data, orario, posizione, biglietti, this);
        gestore.gestisciRichiestaEvento(richiesta, this); // il gestore valuta e gestisce la richiesta
    }

    /**
     * Richiede l'aggiunta di informazioni descrittive a un evento esistente.
     * Anche in questo caso l'animatore invia una richiesta al Gestore,
     * che si occupa di approvarla e inserirla nel sistema.
     *
     * @param evento evento a cui associare le informazioni
     * @param titolo titolo delle informazioni (es. "Programma", "Note logistiche")
     * @param descrizione descrizione testuale dettagliata
     * @param gestore riferimento al Gestore che processa la richiesta
     */
    public void richiediAggiuntaInformazioni(Evento evento,
                                             String titolo,
                                             String descrizione,
                                             Gestore gestore) {
        InformazioniTestuali info = new InformazioniTestuali(titolo, descrizione);
        RichiestaInformazioni richiesta = new RichiestaInformazioni(titolo, this, evento.getNome(), info);
        gestore.gestisciRichiestaInformazioni(richiesta, this); // delega al gestore
        System.out.println("[Animatore] Richiesta informazioni inviata per l'evento: " + evento.getNome());
    }
}
