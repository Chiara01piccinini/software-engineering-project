package com.unicam.ecommerce.Service;

import com.unicam.ecommerce.Component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

/**
 * ContentFactory rappresenta il pattern Factory.
 *
 * La responsabilità principale di questa classe è creare oggetti complessi del sistema
 * (Prodotto, ProdottoTrasformato, Pacchetto, Evento, Account) senza esporre la logica
 * di costruzione direttamente agli altri componenti dell'applicazione.
 *
 * Viene inoltre gestita l'integrazione con altri servizi, come:
 *  - Aggiunta degli oggetti al Marketplace
 *  - Invio di notifiche tramite EmailSystem
 *  - Aggiornamento della mappa del Marketplace
 *
 * Funzionalità principali:
 * 1. creaProdotto → crea un Prodotto base o un ProdottoTrasformato a seconda del tipo di venditore.
 * 2. creaPacchetto → costruisce un pacchetto con prodotti e percentuale di sconto.
 * 3. creaEvento → costruisce un evento, aggiungendolo al marketplace e alla mappa.
 * 4. creaAccount → costruisce un account e collega il componente corrispondente, aggiornando il marketplace e inviando notifiche.
 *
 * Questo approccio consente di centralizzare la logica di creazione dei contenuti e
 * garantisce consistenza e riusabilità.
 */
@Service
public class ContentFactory {

    private final Marketplace sistema;     // Riferimento al marketplace per aggiungere oggetti
    private final EmailSystem notifiche;   // Servizio per invio email di notifica

    @Autowired
    public ContentFactory(Marketplace sistema, EmailSystem notifiche) {
        this.sistema = sistema;
        this.notifiche = notifiche;
    }

    /**
     * Crea un prodotto e lo aggiunge al marketplace.
     * Il tipo di prodotto (base o trasformato) dipende dal tipo di venditore.
     */
    public void creaProdotto(String nome, Azienda azienda, int quantita, Venditore sender) {
        boolean esiste = sistema.getProdotti().values().stream()
                .anyMatch(p -> p.getNome().equals(nome));
        if (esiste) {
            notifiche.inviaMail(sender.getAccount().getEmail(), "Prodotto già esistente",
                    "Il prodotto: " + nome + " è già presente nel sistema");
            return;
        }
        if (sender instanceof Produttore produttore) {
            Prodotto prodottoBase = new Prodotto(nome, azienda, quantita);
            sistema.aggiungiProdotto(prodottoBase);
        } else if (sender instanceof Trasformatore trasformatore) {
            ProdottoTrasformato prodottoElaborato = new ProdottoTrasformato(nome, azienda, quantita);
            sistema.aggiungiProdotto(prodottoElaborato);
        }
        notifiche.inviaMail(sender.getAccount().getEmail(), "Prodotto creato",
                "Il prodotto: " + nome + " è stato aggiunto al sistema");
    }

    /**
     * Crea un pacchetto di prodotti e lo aggiunge al marketplace.
     */
    public void creaPacchetto(Map<Integer, Prodotto> prodotti, String nome,
                              BigDecimal percentualeSconto, int quantita,
                              DistributoreDiTipicita sender) {
        boolean esiste = sistema.getProdotti().values().stream()
                .anyMatch(p -> p.getNome().equals(nome));
        if (esiste) {
            notifiche.inviaMail(sender.getAccount().getEmail(), "Pacchetto già esistente",
                    "Il pacchetto: " + nome + " è già presente nel sistema");
            return;
        }
        Pacchetto p = new Pacchetto(prodotti, nome, percentualeSconto, quantita);
        sistema.aggiungiPacchetto(p);
        notifiche.inviaMail(sender.getAccount().getEmail(), "Pacchetto creato",
                "Il pacchetto: " + nome + " è stato aggiunto al sistema");
    }

    /**
     * Crea un evento e lo aggiunge al marketplace e alla mappa.
     */
    public void creaEvento(LocalDate data, LocalTime orario, Posizione luogo,
                           String nome, int b, Animatore sender) {
        boolean esiste = sistema.getEventi().values().stream()
                .anyMatch(e -> e.getNome().equals(nome) && e.getData().equals(data));
        if (esiste) {
            notifiche.inviaMail(sender.getAccount().getEmail(), "Evento già esistente",
                    "L'evento: " + nome + " è già presente nel sistema");
            return;
        }
        Evento evento = new Evento(nome, luogo, data, orario, b);
        sistema.aggiungiEvento(evento);
        sistema.getMappa().aggiungiLuogo(luogo, nome);
        notifiche.inviaMail(sender.getAccount().getEmail(), "Evento creato",
                "Evento: " + nome + " è stato aggiunto al sistema");
    }

    /**
     * Crea un account e lo associa a un componente.
     * Aggiunge l'account al marketplace, aggiorna la mappa se necessario e invia mail di benvenuto.
     */
    public void creaAccount(String nomeUtente, String email, String password,
                            TipoAccount tipo, Componente sender) {
        boolean esiste = sistema.getProfili().values().stream()
                .anyMatch(acc -> acc.getNomeUtente().equals(nomeUtente));
        if (esiste) {
            System.out.println("[Messaggio]: Account già esistente: " + nomeUtente);
            return;
        }
        Account account = new Account(nomeUtente, email, password, tipo);
        account.setComponente(sender);
        sender.setAccount(account);
        sistema.aggiungiAccount(account);

        if(sender instanceof Venditore){
            sistema.getMappa().aggiungiLuogo(((Venditore) sender).getAzienda().getPosizione(),
                    ((Venditore) sender).getAzienda().getNome());
        }

        notifiche.inviaMail(email, "BENVENUTO",
                "Il tuo account è stato creato. Ora potrai acquistare tutti i prodotti disponibili");
    }
}
