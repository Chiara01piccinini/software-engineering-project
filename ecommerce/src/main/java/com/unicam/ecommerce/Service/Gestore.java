package com.unicam.ecommerce.Service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicam.ecommerce.Component.*;
import com.unicam.ecommerce.JsonPersistence.PersistenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Gestore estende Componente ed è responsabile della gestione delle richieste
 * inviate dai venditori, distributori e animatori verso il Curatore.
 *
 * Compiti principali:
 * 1. Inoltra richieste di prodotti, pacchetti, eventi e informazioni al Curatore.
 * 2. Esegue richieste approvate dal Curatore tramite il ContentFactory.
 * 3. Gestisce acquisti del carrello, verificando disponibilità e pagamenti.
 * 4. Pubblica contenuti sponsorizzati tramite il SistemaSocial.
 * 5. Invia notifiche via EmailSystem agli utenti coinvolti.
 *
 * Nota:
 * Rappresenta il ruolo centrale nel flusso di approvazione e gestione del marketplace.
 */
@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gestore extends Componente implements Mediator  {

    private final Curatore curatore;
    private final EmailSystem notifiche;
    private final ContentFactory creatore;
    private final Marketplace sistema;
    private final SistemaSocial social;
    private final PersistenceManager persistenceManager;


    public Gestore(String nome,String cognome, Curatore curatore, EmailSystem notifiche,
                   ContentFactory creatore, Marketplace sistema, SistemaSocial social, PersistenceManager persistenceManager) {
        super(nome,cognome,TipoAccount.GESTORE);
        this.curatore = curatore;
        this.notifiche = notifiche;
        this.creatore = creatore;
        this.sistema = sistema;
        this.social = social;
        this.persistenceManager = persistenceManager;
    }
    @Autowired
    public Gestore(Curatore curatore, EmailSystem notifiche,
                   ContentFactory creatore, Marketplace sistema,
                   SistemaSocial social, PersistenceManager persistenceManager) {
        super("", "", TipoAccount.GESTORE); // nome e cognome vuoti inizialmente
        this.curatore = curatore;
        this.notifiche = notifiche;
        this.creatore = creatore;
        this.sistema = sistema;
        this.social = social;
        this.persistenceManager = persistenceManager;
    }

    public Gestore() {
        super("", "", TipoAccount.GESTORE);
        this.curatore = null;
        this.notifiche = null;
        this.creatore = null;
        this.sistema = null;
        this.social = null;
        this.persistenceManager = null;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }

    @Override
    public void setCognome(String cognome) {
        super.setCognome(cognome);
    }

    // ---------- Gestione richieste dai venditori/distributori/animatori ----------

    public void gestisciRichiestaProdotto(RichiestaProdotto richiesta, Venditore sender) {
        richiesta.setMittente(sender);
        curatore.riceviRichiestaProdotto(richiesta, this);
        notifiche.inviaMail(sender.getAccount().getEmail(),
                "Richiesta inviata",
                "La tua richiesta di prodotto '" + richiesta.getNome() + "' è in attesa di approvazione.");
    }

    public void gestisciRichiestaPacchetto(RichiestaPacchetto richiesta, DistributoreDiTipicita sender) {
        richiesta.setMittente(sender);
        curatore.riceviRichiestaPacchetto(richiesta, this);
        notifiche.inviaMail(sender.getAccount().getEmail(),
                "Richiesta inviata",
                "La tua richiesta di pacchetto '" + richiesta.getNome() + "' è in attesa di approvazione.");
    }

    public void gestisciRichiestaEvento(RichiestaEvento richiesta, Animatore sender) {
        richiesta.setMittente(sender);
        curatore.riceviRichiestaEvento(richiesta, this);
        notifiche.inviaMail(sender.getAccount().getEmail(),
                "Richiesta inviata",
                "La tua richiesta di evento '" + richiesta.getNome() + "' è in attesa di approvazione.");
    }

    public void gestisciRichiestaInformazioni(RichiestaInformazioni richiesta, Componente sender) {
        richiesta.setMittente(sender);
        curatore.riceviRichiestaInformazioni(richiesta, this);
        notifiche.inviaMail(sender.getAccount().getEmail(),
                "Richiesta informazioni inviata",
                "La tua richiesta di aggiungere informazioni per il prodotto '" +
                        richiesta.getNomeProdotto() + "' è in attesa di approvazione.");
    }

    // ---------- Esecuzione richieste approvate ----------

    public void eseguiRichiestaProdotto(RichiestaProdotto richiesta) throws IOException {
        creatore.creaProdotto(richiesta.getNome(), richiesta.getAzienda(), richiesta.getQuantita(),
                (Venditore) richiesta.getMittente());
        persistenceManager.salva();
        notifiche.inviaMail(richiesta.getMittente().getAccount().getEmail(),
                "Richiesta approvata",
                "Il prodotto '" + richiesta.getNome() + "' è stato approvato e inserito nel sistema.");
    }

    public void eseguiRichiestaPacchetto(RichiestaPacchetto richiesta) throws IOException {
        creatore.creaPacchetto(richiesta.getProdotti(), richiesta.getNome(),
                richiesta.getPercentualeSconto(), richiesta.getQuantita(),
                (DistributoreDiTipicita) richiesta.getMittente());
        persistenceManager.salva();
        notifiche.inviaMail(richiesta.getMittente().getAccount().getEmail(),
                "Richiesta approvata",
                "Il pacchetto '" + richiesta.getNome() + "' è stato approvato e inserito nel sistema.");
    }

    public void eseguiRichiestaEvento(RichiestaEvento richiesta) throws IOException {
        creatore.creaEvento(richiesta.getData(), richiesta.getOrario(), richiesta.getLuogo(),
                richiesta.getNome(), richiesta.getBiglietti(),
                (Animatore) richiesta.getMittente());
        persistenceManager.salva();
        notifiche.inviaMail(richiesta.getMittente().getAccount().getEmail(),
                "Richiesta approvata",
                "L'evento '" + richiesta.getNome() + "' è stato approvato e inserito nel sistema.");
    }

    public void eseguiRichiestaInformazioni(RichiestaInformazioni richiesta) throws IOException {
        persistenceManager.carica();
        Componente mittente = richiesta.getMittente();
        IElemento elemento = null;

        if (mittente instanceof Trasformatore trasformatore) {
            for (Prodotto p : sistema.getProdotti().values()) {
                if (p.getNome().equals(richiesta.getNomeProdotto()) &&
                        p.getAzienda().equals(trasformatore.getAzienda())) {
                    elemento = p;
                    break;
                }
            }
            if (elemento instanceof ProdottoTrasformato pt) pt.setProduzione(richiesta.getInfo());
        } else if (mittente instanceof DistributoreDiTipicita) {
            for (Pacchetto pac : sistema.getPacchetti().values()) {
                if (pac.getNome().equals(richiesta.getNomeProdotto())) {
                    elemento = pac;
                    break;
                }
            }
            if (elemento instanceof Pacchetto pac) pac.setDescrizione(richiesta.getInfo());
        } else if (mittente instanceof Animatore) {
            for (Evento ev : sistema.getEventi().values()) {
                if (ev.getNome().equals(richiesta.getNomeProdotto())) {
                    ev.setDescrizione(richiesta.getInfo());
                    break;
                }
            }
        }

        if (elemento == null) {
            System.out.println("[Gestore] Nessun elemento trovato con nome: " + richiesta.getNomeProdotto());
            return;
        }

        persistenceManager.salva();

        notifiche.inviaMail(
                richiesta.getMittente().getAccount().getEmail(),
                "Richiesta informazioni approvata",
                "Le informazioni aggiuntive per '" + richiesta.getNomeProdotto() + "' sono state approvate e inserite nel sistema."
        );
    }

    // ---------- Metodi generici ----------

    public void rifiutaRichiesta(Richiesta richiesta) {
        notifiche.inviaMail(richiesta.getMittente().getAccount().getEmail(),
                "Richiesta rifiutata",
                "La tua richiesta '" + richiesta.getNome() + "' non è stata approvata dal curatore.");
    }

    public void acquistaCarrello(Componente componente) throws IOException {
        persistenceManager.carica();
        Carrello carrello = componente.getAccount().getCarrello();
        Map<IElemento, Integer> prodotti = carrello.getProdotti();

        BigDecimal totale = BigDecimal.ZERO;

        for (Map.Entry<IElemento, Integer> entry : prodotti.entrySet()) {
            IElemento elemento = entry.getKey();
            int quantita = entry.getValue();

            if ((elemento instanceof Prodotto p && quantita > p.getQuantita()) ||
                    (elemento instanceof Pacchetto pac && quantita > pac.getQuantita())) {
                notifiche.inviaMail(componente.getAccount().getEmail(),
                        "Acquisto non avvenuto",
                        "Quantità richiesta non disponibile per: " + elemento.getNome());
                return;
            }

            totale = totale.add(elemento.calcolaPrezzo().multiply(BigDecimal.valueOf(quantita)));
        }

        boolean tuttiPagamentiRiusciti = true;
        for (Map.Entry<IElemento, Integer> entry : prodotti.entrySet()) {
            IElemento elemento = entry.getKey();
            int quantita = entry.getValue();
            BigDecimal importo = elemento.calcolaPrezzo().multiply(BigDecimal.valueOf(quantita));

            if (!SistemaPagamenti.eseguiPagamento(componente, elemento, importo)) {
                tuttiPagamentiRiusciti = false;
                System.out.println("[Messaggio]: Pagamento fallito per " + elemento.getNome());
            }
        }

        if (!tuttiPagamentiRiusciti) {
            notifiche.inviaMail(componente.getAccount().getEmail(),
                    "Pagamento non avvenuto",
                    "Pagamento carrello fallito. Nessun addebito effettuato.");
            return;
        }

        for (Map.Entry<IElemento, Integer> entry : prodotti.entrySet()) {
            IElemento elemento = entry.getKey();
            int quantita = entry.getValue();
            elemento.setQuantita(elemento.getQuantita() - quantita);
            if (elemento.getQuantita() <= 0) {
                if (elemento instanceof Prodotto p) sistema.rimuoviProdotto(p.getId());
                else if (elemento instanceof Pacchetto pac) sistema.rimuoviPacchetto(pac.getId());
            }
        }

        carrello.getProdotti().clear();
        persistenceManager.salva();

        notifiche.inviaMail(componente.getAccount().getEmail(),
                "Acquisto avvenuto",
                "Pagamento carrello completato. Totale speso: " + totale + " EUR");
    }

    public void pubblicaContenuto(Venditore venditore, RichiestaSponsorizzazione messaggio, Piattaforme piattaforma) throws IOException {
        persistenceManager.carica();
        if (sistema.getProfili().containsKey(venditore.getAccount().getId())){
            System.out.println("[GestoreSponsorizzazioni] Richiesta ricevuta dal venditore: " + venditore.getNome());
            social.pubblica(messaggio,piattaforma);
            notifiche.inviaMail(venditore.getAccount().getEmail(),"contenuto pubblicato",
                    "Il prodotto " + messaggio.getNome() + " è stato pubblicato su " +  piattaforma);
        }
    }

    // ---------- Nuovo metodo per eliminare un account dal marketplace ----------
    public boolean eliminaAccount(String username) throws IOException {
        persistenceManager.carica();
        Account account = sistema.getAccountByUsername(username);
        if (account == null) {
            System.out.println("[Gestore] Account non trovato: " + username);
            return false;
        }
        sistema.getProfili().remove(account.getId());
        persistenceManager.salva();
        System.out.println("[Gestore] Account eliminato: " + username);
        notifiche.inviaMail(account.getEmail(), "Account eliminato",
                "Il tuo account è stato rimosso dal marketplace.");
        return true;
    }
}
