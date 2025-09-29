package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicam.ecommerce.Service.Gestore;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Curatore rappresenta una figura che funge da "controllore" nel sistema.
 * Si occupa di ricevere richieste (prodotti, pacchetti, eventi, informazioni)
 * provenienti dal Gestore e decide se approvarle o rifiutarle.
 *
 * Il Curatore mantiene una lista di richieste pendenti che devono essere valutate.
 */
@Component
public class Curatore extends Componente {

    // Lista di tutte le richieste in attesa di approvazione
    private List<Richiesta> richiestePendenti = new ArrayList<>();

    // ==============================
    // COSTRUTTORI
    // ==============================

    @JsonCreator
    public Curatore(@JsonProperty("tipo") TipoAccount tipo,@JsonProperty("nome") String nome,@JsonProperty("cognome") String cognome,@JsonProperty("richiestePendenti") List<Richiesta> richiestePendenti) {
        super(nome, cognome, tipo);
        this.richiestePendenti = richiestePendenti;
    }

    public Curatore(TipoAccount tipo,String nome, String cognome) {
        super(nome, cognome, tipo);
    }

    public Curatore(){
        super();
    };

    // ==============================
    // METODI PER RICEVERE RICHIESTE
    // ==============================

    /**
     * Riceve una richiesta di aggiunta prodotto.
     */
    public void riceviRichiestaProdotto(RichiestaProdotto richiesta, Gestore gestore) {
        richiestePendenti.add(richiesta);
        System.out.println("[Curatore] Ricevuta richiesta prodotto: " + richiesta.getNome());
    }

    /**
     * Riceve una richiesta di aggiunta pacchetto.
     */
    public void riceviRichiestaPacchetto(RichiestaPacchetto richiesta, Gestore gestore) {
        richiestePendenti.add(richiesta);
        System.out.println("[Curatore] Ricevuta richiesta pacchetto: " + richiesta.getNome());
    }

    /**
     * Riceve una richiesta di aggiunta evento.
     */
    public void riceviRichiestaEvento(RichiestaEvento richiesta, Gestore gestore) {
        richiestePendenti.add(richiesta);
        System.out.println("[Curatore] Ricevuta richiesta evento: " + richiesta.getNome());
    }

    /**
     * Riceve una richiesta di aggiunta informazioni su un prodotto o evento.
     */
    public void riceviRichiestaInformazioni(RichiestaInformazioni richiesta, Gestore gestore) {
        richiestePendenti.add(richiesta);
        System.out.println("[Curatore] Ricevuta richiesta informazioni per: " + richiesta.getNomeProdotto());
    }

    // ==============================
    // METODI DI APPROVAZIONE/RIFIUTO
    // ==============================

    /**
     * Valida ed approva o rifiuta una richiesta di prodotto.
     */
    public void approvaProdotto(RichiestaProdotto richiesta, Gestore gestore) throws IOException {
        if (richiesta.getNome() != null && richiesta.getQuantita() > 0 && richiesta.getAzienda() != null) {
            richiestePendenti.remove(richiesta);
            gestore.eseguiRichiestaProdotto(richiesta);
        } else {
            richiestePendenti.remove(richiesta);
            gestore.rifiutaRichiesta(richiesta);
        }
    }

    /**
     * Valida ed approva o rifiuta una richiesta di pacchetto.
     */
    public void approvaPacchetto(RichiestaPacchetto richiesta, Gestore gestore) throws IOException {
        if (richiesta.getNome() != null &&
                richiesta.getProdotti() != null &&
                !richiesta.getProdotti().isEmpty() &&
                richiesta.getPercentualeSconto().compareTo(java.math.BigDecimal.ZERO) >= 0 &&
                richiesta.getPercentualeSconto().compareTo(new java.math.BigDecimal("100")) <= 0) {

            richiestePendenti.remove(richiesta);
            gestore.eseguiRichiestaPacchetto(richiesta);
        } else {
            richiestePendenti.remove(richiesta);
            gestore.rifiutaRichiesta(richiesta);
        }
    }

    /**
     * Valida ed approva o rifiuta una richiesta di evento.
     */
    public void approvaEvento(RichiestaEvento richiesta, Gestore gestore) throws IOException {
        if (richiesta.getNome() != null &&
                richiesta.getData() != null &&
                richiesta.getOrario() != null &&
                richiesta.getBiglietti() > 0 &&
                richiesta.getLuogo() != null) {

            richiestePendenti.remove(richiesta);
            gestore.eseguiRichiestaEvento(richiesta);
        } else {
            richiestePendenti.remove(richiesta);
            gestore.rifiutaRichiesta(richiesta);
        }
    }

    /**
     * Approva una richiesta di aggiunta informazioni.
     */
    public void approvaRichiestaInformazioni(RichiestaInformazioni richiesta, Gestore gestore) throws IOException {
        richiestePendenti.remove(richiesta);
        gestore.eseguiRichiestaInformazioni(richiesta);
    }

    /**
     * Rifiuta una richiesta di aggiunta informazioni.
     */
    public void rifiutaRichiestaInformazioni(RichiestaInformazioni richiesta, Gestore gestore) {
        richiestePendenti.remove(richiesta);
        gestore.rifiutaRichiesta(richiesta);
    }

    // ==============================
    // METODI DI SUPPORTO
    // ==============================

    /**
     * Restituisce tutte le richieste pendenti.
     */
    public List<Richiesta> getRichiestePendenti() {
        return richiestePendenti;
    }

    /**
     * Restituisce una lista leggibile delle richieste pendenti,
     * con i dettagli principali a seconda del tipo di richiesta.
     */
    public List<String> visualizzaRichiestePendenti() {
        List<String> lista = new ArrayList<>();
        for (Richiesta r : richiestePendenti) {
            String contenuto = "ID: " + r.getId() + ", Nome: " + r.getNome();

            if (r instanceof RichiestaProdotto rp) {
                Azienda az = rp.getAzienda();
                String nomeAzienda = (az != null) ? az.getNome() : "N/A";
                contenuto += ", Quantità: " + rp.getQuantita() + ", Azienda: " + nomeAzienda;

            } else if (r instanceof RichiestaPacchetto pac) {
                String prodotti = (pac.getProdotti() != null && !pac.getProdotti().isEmpty())
                        ? pac.getProdotti().values().stream()
                        .map(Prodotto::getNome)
                        .toList().toString()
                        : "Nessun prodotto";
                contenuto += ", Prodotti: " + prodotti
                        + ", Sconto: " + (pac.getPercentualeSconto() != null ? pac.getPercentualeSconto() : "N/A");

            } else if (r instanceof RichiestaEvento ev) {
                contenuto += ", Data: " + (ev.getData() != null ? ev.getData() : "N/A")
                        + ", Orario: " + (ev.getOrario() != null ? ev.getOrario() : "N/A")
                        + ", Luogo: " + (ev.getLuogo() != null ? ev.getLuogo() : "N/A");
            }

            lista.add(contenuto);
        }
        return lista;
    }
}
