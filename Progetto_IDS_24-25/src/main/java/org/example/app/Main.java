package org.example.app;

import org.example.app.controls.GestoreAcquisti;
import org.example.app.controls.GestoreCreazioni;
import org.example.app.controls.GestorePubblicazioni;
import org.example.app.model.*;
import org.example.app.model.JsonPersistence.PersistenceManager;
import org.example.app.view.EmailSystem;
import org.example.app.view.SistemaPagamenti;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Main {

    // Inizializzazione globale
    static Marketplace marketplace = new Marketplace();
    static Curatore curatore = new Curatore(new Account("curatore1", "pass", tipoAccount.GENERICO), 1, "EMAIL_CURATORE@gmail.com",new EmailSystem());
    static GestorePubblicazioni gestorePubblicazioni = new GestorePubblicazioni(curatore, new EmailSystem());
    static GestoreCreazioni gestoreCreazioni = new GestoreCreazioni(gestorePubblicazioni, curatore);
    static PersistenceManager persistenceManager = new PersistenceManager();

    public static void main(String[] args) {
        gestorePubblicazioni.setGestoreCreazioni(gestoreCreazioni);

        try {
            persistenceManager.carica();
        } catch (IOException e) {
            e.printStackTrace();
        }

        testCreazioneAccount();
        testCreazionePacchetto();
        testAcquisto();
        testPrenotazioneEvento();

        try {
            persistenceManager.salva();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------------- TEST CREAZIONE ACCOUNT ----------------------
    public static void testCreazioneAccount() {
        System.out.println(">>> Test creazione account");
        FileInformazioniAccount info = new FileInformazioniAccount("mario", "password123", "mario@email.com", tipoAccount.GENERICO);

        Componente sender = new Produttore(new Account("produttore1", "pass", tipoAccount.PRODUTTORE), 1, "produttore@email.com", new Azienda("Azienda Mario", "cod123"));
        gestoreCreazioni.creaAccount(info, sender);

        marketplace.getAccount().forEach((id, account) -> {
            System.out.println("ID: " + id + ", Username: " + account.getNomeUtente() + ", Tipo: " + account.getTipologia());
        });
        System.out.println();
        try {
            persistenceManager.salva();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------------- TEST CREAZIONE PACCHETTO ----------------------
    public static void testCreazionePacchetto() {
        System.out.println(">>> Test creazione pacchetto");

        // Prodotti
        Produttore produttore = new Produttore(new Account("prod1", "pass", tipoAccount.PRODUTTORE), 1, "prod1@email.com", new Azienda("Frutta", "cod1"));
        Trasformatore trasformatore = new Trasformatore(new Account("tras1", "pass", tipoAccount.TRASFORMATORE), 2, "tras1@email.com", new Azienda("Salumi", "cod2"));

        Prodotto p1 = new ProdottoBase("Pere", produttore.getAzienda(), produttore);
        Prodotto p2 = new ProdottoElaborato("Salame Toscano", trasformatore.getAzienda(), trasformatore);

        Set<Prodotto> prodotti = new HashSet<>();
        prodotti.add(p1);
        prodotti.add(p2);

        FileInformazioniPacchetto pacchettoInfo = new FileInformazioniPacchetto("Degustazione Tipica", BigDecimal.valueOf(10), prodotti, 5);

        gestorePubblicazioni.inviaPacchetto(produttore, pacchettoInfo);
        System.out.println();
        try {
            persistenceManager.salva();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------------- TEST ACQUISTO ----------------------
    public static void testAcquisto() {
        System.out.println(">>> Test acquisto prodotto e pacchetto");

        Componente acquirente = new Componente(new Account("acquirente1", "pass", tipoAccount.GENERICO), 1, "acquirente@email.com");

        // Prodotto
        Produttore produttore = new Produttore(new Account("prod2", "pass", tipoAccount.PRODUTTORE), 2, "prod2@email.com", new Azienda("Frutta2", "cod3"));
        Prodotto prodotto = new ProdottoBase("Mele Golden", produttore.getAzienda(), produttore);
        prodotto.setPrezzo(BigDecimal.valueOf(2.5));
        prodotto.setVendita(true);
        marketplace.aggiungiProdotto(prodotto);

        // Pacchetto
        Set<Prodotto> pacchettoProdotti = new HashSet<>();
        pacchettoProdotti.add(prodotto);
        Pacchetto pacchetto = new Pacchetto("Pacchetto Degustazione", BigDecimal.valueOf(10), pacchettoProdotti);
        pacchetto.setPrezzo(BigDecimal.valueOf(2.5));
        marketplace.aggiungiPacchetto(pacchetto);

        GestoreAcquisti gestoreAcquisti = new GestoreAcquisti(new SistemaPagamenti(), marketplace);
        gestoreAcquisti.acquistaProdotto(acquirente, prodotto, 3);
        gestoreAcquisti.acquistaPacchetto(acquirente, pacchetto);

        System.out.println();
        try {
            persistenceManager.salva();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------------- TEST PRENOTAZIONE EVENTO ----------------------
    public static void testPrenotazioneEvento() {
        System.out.println(">>> Test prenotazione evento");

        // Creazione dati necessari
        Date dataEvento = new Date(); // data odierna, puoi modificarla
        LocalDateTime orarioEvento = LocalDateTime.now().plusDays(1); // domani
        Position luogoEvento = new Position("luogo evento",45.4642, 9.1900); // esempio coordinate
        String nomeEvento = "Concerto Rock";
        int bigliettiEvento = 100;
        String descrizione = "Vivi una serata di pura energia con musica live, band locali e i grandi classici del rock!\n" +
                "Cibo genuino, atmosfera unica e prodotti tipici della nostra filiera ti aspettano per un’esperienza indimenticabile.";

        // Creazione animatore
        Animatore animatore = new Animatore(new Account("anim1", "pass", tipoAccount.ANIMATORE), 1, "animatore@email.com");

        // Creazione evento usando il costruttore completo
        Evento evento = new Evento(dataEvento, orarioEvento, luogoEvento, nomeEvento, bigliettiEvento,descrizione);

        // Aggiunta al marketplace
        marketplace.aggiungiEvento(evento);

        // Test biglietti
        System.out.println("Biglietti iniziali: " + evento.getBiglietti());
        evento.rimuoviBiglietto(3);
        System.out.println("Biglietti rimasti: " + evento.getBiglietti());
        System.out.println();
        try {
            persistenceManager.salva();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
