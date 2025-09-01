package org.example.app;

import org.example.app.model.*;
import org.example.app.model.JsonPersistence.PersistenceManager;
import org.example.app.view.*;
import org.example.app.controls.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Main {

    // Inizializzazione globale
    static Marketplace marketplace = new Marketplace();
    static Curatore curatore = new Curatore(new Account("curatore1", "pass", tipoAccount.CURATORE), 1, "mrzpccnn@gmail.com",new EmailSystem());
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

        //testCreazioneAccounteautenticazione();
        //testCreazioneContenuti();
        testAcquisto();
        //testPrenotazioneEvento();
        //testSponsorizzazioneProdotto();


        try {
            persistenceManager.salva();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testCreazioneContenuti() {
        System.out.println(">>> Test creazione contenuti START");

        try {
            // 1. Creazione account curatore
            FileInformazioniAccount curatoreInfo = new FileInformazioniAccount(
                    "curatore1",
                    "pass",
                    "curatore@email.com",
                    tipoAccount.CURATORE
            );

            Curatore sender = new Curatore(new Account(curatoreInfo.getNomeUtente(), curatoreInfo.getPassword(), tipoAccount.CURATORE), 0, "system@test.com", new EmailSystem());
            gestoreCreazioni.creaAccount(curatoreInfo, sender);

            Account curatoreAccount = marketplace.getAccount().values().stream()
                    .filter(a -> a.getNomeUtente().equals("curatore1"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Account curatore non trovato!"));

            Curatore curatore = new Curatore(curatoreAccount, 1, sender.getEmail(), new EmailSystem());

            // 2. Posizioni aziende ed eventi
            Position luogoAzienda1 = new Position("luogo azienda 1", 15.46, 9.10);
            Position luogoEvento = new Position("Piazza Centrale", 45.123, 9.456);

            // 3. Creazione altri utenti
            Produttore produttore = new Produttore(
                    new Account("prod1", "pass", tipoAccount.PRODUTTORE),
                    2,
                    "prod1@email.com",
                    new Azienda("Frutta", luogoAzienda1, "cod1")
            );

            Animatore animatore = new Animatore(
                    4,
                    "animatore@email.com",
                    new Account("anim1", "pass", tipoAccount.ANIMATORE)
            );

            // 4. Autenticazione curatore
            AuthHandler roleCheck = new RoleCheckHandler(marketplace);
            AuthHandler credentialCheck = new CredentialCheckHandler(marketplace);
            roleCheck.linkWith(credentialCheck);
            AuthService authService = new AuthService(roleCheck);

            if (!authService.logIn("curatore1", "pass")) {
                System.out.println("[Test] Autenticazione fallita");
                return;
            }

            // 5. Creazione prodotti e messa in vendita
            Prodotto p1 = new ProdottoBase("Pere", produttore.getAzienda(), produttore);
            Prodotto p2 = new ProdottoBase("Mele", produttore.getAzienda(), produttore);

            // Imposto prezzo, quantità e vendita
            p1.setPrezzo(BigDecimal.valueOf(2.5));
            p1.setQuantita(10);
            p1.setVendita(true);

            p2.setPrezzo(BigDecimal.valueOf(3.0));
            p2.setQuantita(8);
            p2.setVendita(true);

            gestorePubblicazioni.inviaProdotto(produttore, new FileInformazioniProdotto("Pere Fresche", produttore.getAzienda()));
            gestorePubblicazioni.inviaProdotto(produttore, new FileInformazioniProdotto("Mele Fresche", produttore.getAzienda()));
            System.out.println("[Debug] Prodotti inviati e messi in vendita");

            // 6. Creazione pacchetto con distributore
            DistributoreDiTipicita distributore = new DistributoreDiTipicita(
                    new Account("dist1", "distpass", tipoAccount.DISTRIBUTOREDITIPICITA),
                    5,
                    "dist1@email.com",
                    new Azienda("DistribuzioniSRL", luogoAzienda1, "codDist")
            );

            Set<Prodotto> prodottiPacchetto = new HashSet<>();
            prodottiPacchetto.add(p1);
            prodottiPacchetto.add(p2);

            FileInformazioniPacchetto pacchettoInfo = new FileInformazioniPacchetto(
                    "Degustazione Tipica",
                    BigDecimal.valueOf(10),
                    prodottiPacchetto,
                    5
            );

            gestorePubblicazioni.inviaPacchetto(distributore, pacchettoInfo);

            // 7. Creazione evento
            FileInformazioniEvento eventoInfo = new FileInformazioniEvento(
                    new Date(),
                    LocalDateTime.now(),
                    luogoEvento,
                    "Fiera del Gusto",
                    100,
                    "Degustazioni e spettacoli"
            );
            gestorePubblicazioni.inviaEvento(animatore, eventoInfo);

        } catch (Exception e) {
            System.err.println("[FATAL] Errore durante il test creazione contenuti");
            e.printStackTrace();
        } finally {
            try {
                Session.logout();
                System.out.println("[Test] Curatore disconnesso");
            } catch (Exception e) {
                System.err.println("[Error] Logout fallito");
                e.printStackTrace();
            }
        }

        System.out.println(">>> Test creazione contenuti END");
    }

    public static void testAcquisto() {
        System.out.println(">>> Test creazione account e acquisto START");

        try {
            // 1. Creazione account acquirente
            FileInformazioniAccount info = new FileInformazioniAccount(
                    "acquirente1",
                    "pass",
                    "acquirente@email.com",
                    tipoAccount.GENERICO
            );
            Componente sender = new Componente(1, "system@email.com");
            gestoreCreazioni.creaAccount(info, sender);

            Account accountAcquirente = marketplace.getAccountByUsername("acquirente1");
            Componente acquirente = new Componente(1, "acquirente@email.com");
            acquirente.setAccount(accountAcquirente);

            // 2. Autenticazione acquirente
            AuthHandler roleCheck = new RoleCheckHandler(marketplace);
            AuthHandler credentialCheck = new CredentialCheckHandler(marketplace);
            roleCheck.linkWith(credentialCheck);
            AuthService authService = new AuthService(roleCheck);

            if (!authService.logIn("acquirente1", "pass")) {
                throw new RuntimeException("Autenticazione fallita, impossibile procedere con l'acquisto");
            }

            // 3. Creazione produttore e invio prodotti
            Position luogoAzienda = new Position("luogo azienda", 4.4, 9.9);
            Produttore produttore = new Produttore(
                    new Account("prod2", "pass", tipoAccount.PRODUTTORE),
                    2,
                    "prod2@email.com",
                    new Azienda("Frutta2", luogoAzienda, "cod3")
            );

            Prodotto prodotto1 = new ProdottoBase("Mele Golden", produttore.getAzienda(), produttore);
            Prodotto prodotto2 = new ProdottoBase("Banane", produttore.getAzienda(), produttore);

            gestorePubblicazioni.inviaProdotto(produttore, new FileInformazioniProdotto("Mele Golden", produttore.getAzienda()));
            gestorePubblicazioni.inviaProdotto(produttore, new FileInformazioniProdotto("Banane", produttore.getAzienda()));

            prodotto1.setPrezzo(BigDecimal.valueOf(2.5));
            prodotto1.setVendita(true);
            prodotto1.setQuantita(10);

            prodotto2.setPrezzo(BigDecimal.valueOf(3.0));
            prodotto2.setVendita(true);
            prodotto2.setQuantita(15);

            // 4. Creazione distributore e pacchetto con più prodotti
            DistributoreDiTipicita distributore = new DistributoreDiTipicita(
                    new Account("dist1", "distpass", tipoAccount.DISTRIBUTOREDITIPICITA),
                    3,
                    "dist@email.com",
                    new Azienda("DistribuzioniSRL", luogoAzienda, "cod4")
            );

            Set<Prodotto> prodottiPacchetto = new HashSet<>();
            prodottiPacchetto.add(prodotto1);
            prodottiPacchetto.add(prodotto2);

            FileInformazioniPacchetto pacchettoInfo = new FileInformazioniPacchetto(
                    "Pacchetto Degustazione",
                    BigDecimal.valueOf(10),
                    prodottiPacchetto,
                    2
            );

            gestorePubblicazioni.inviaPacchetto(distributore, pacchettoInfo);

            System.out.println("[Debug] Pacchetti presenti nel marketplace:");
            marketplace.getPacchetti().values().forEach(p ->
                    System.out.println(" - " + p.getNome() + " (quantità: " + p.getQuantita() + ", prezzo: " + p.calcolaPrezzo() + ")")
            );

        } catch (Exception e) {
            System.err.println("[FATAL] Errore durante il test acquisto");
            e.printStackTrace();
        } finally {
            try {
                Session.logout();
            } catch (Exception ignored) {}
        }

        System.out.println(">>> Test creazione account e acquisto END");
    }


    // ---------------------- TEST PRENOTAZIONE EVENTO ----------------------
    public static void testPrenotazioneEvento() {
        System.out.println(">>> Test prenotazione evento");

        // ---------------------- CREAZIONE ACCOUNT ----------------------
        FileInformazioniAccount info = new FileInformazioniAccount(
                "utenteEvento",
                "passEvento",
                "utenteEvento@email.com",
                tipoAccount.GENERICO
        );

        Componente sender = new Componente(1, "mittente@email.com");

        // Creazione account tramite gestoreCreazioni
        gestoreCreazioni.creaAccount(info, sender);

        // Recupero account dal marketplace
        Account accountUtente = marketplace.getAccountByUsername("utenteEvento");
        Componente utente = new Componente(1, "utenteEvento@email.com");
        utente.setAccount(accountUtente);

        // ---------------------- AUTENTICAZIONE ----------------------
        AuthHandler roleCheck = new RoleCheckHandler(marketplace);
        AuthHandler credentialCheck = new CredentialCheckHandler(marketplace);
        roleCheck.linkWith(credentialCheck);
        AuthService authService = new AuthService(roleCheck);

        if (authService.logIn("utenteEvento", "passEvento")) {
            System.out.println("[Test] Login riuscito");
        } else {
            throw new RuntimeException("Autenticazione fallita, impossibile procedere con la prenotazione");
        }

        // ---------------------- CREAZIONE EVENTO ----------------------
        Date dataEvento = new Date();
        LocalDateTime orarioEvento = LocalDateTime.now().plusDays(1);
        Position luogoEvento = new Position("luogo evento", 45.4642, 9.1900);
        String nomeEvento = "Concerto Rock";
        int bigliettiEvento = 100;
        String descrizione = "Vivi una serata di pura energia con musica live, band locali e i grandi classici del rock!";

        FileInformazioniEvento infoEvento = new FileInformazioniEvento(dataEvento, orarioEvento, luogoEvento, nomeEvento, bigliettiEvento, descrizione);

        // Creazione evento tramite gestoreCreazioni
        Animatore animatore = new Animatore(1,"animatore@email.com",new Account("anim1", "pass", tipoAccount.ANIMATORE));
        gestoreCreazioni.creaEvento(infoEvento, animatore);

        // ---------------------- PRENOTAZIONE ----------------------
        Evento evento = marketplace.getEventi().values().stream().filter(e -> e.getNome().equals(nomeEvento)).findFirst().orElseThrow();
        System.out.println("Biglietti iniziali: " + evento.getBiglietti());
        utente.prenotaEvento(evento, 3);
        System.out.println("Biglietti rimasti: " + evento.getBiglietti());

        // Mostra le prenotazioni dell'utente
        System.out.println("Prenotazioni utente:");
        utente.getPrenotazioni();

        // ---------------------- STAMPA MAPPA DEL GESTORE ----------------------
        System.out.println("Mappa dei luoghi dal GestoreCreazioni:");
        gestoreCreazioni.getMappa().stampaMappa();

        // Logout
        Session.logout();

        // Salvataggio dati
        try {
            persistenceManager.salva();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void testSponsorizzazioneProdotto() {
        System.out.println(">>> Test sponsorizzazione prodotto");

        // ---------------------- CREAZIONE ACCOUNT VENDITORE ----------------------
        FileInformazioniAccount infoVenditore = new FileInformazioniAccount(
                "venditore1",
                "passVenditore",
                "venditore1@email.com",
                tipoAccount.PRODUTTORE
        );

        Azienda azienda = new Azienda("FruttaStore", new Position("SedeFrutta", 45.4642, 9.1900), "cod1");
        Produttore venditore = new Produttore(null, 1, infoVenditore.getEmail(), azienda);

        // Creazione account tramite GestoreCreazioni
        gestoreCreazioni.creaAccount(infoVenditore, venditore);

        // Recupero account dal marketplace e associa al venditore
        Account accountVenditore = marketplace.getAccountByUsername("venditore1");
        venditore.setAccount(accountVenditore);

        // ---------------------- AUTENTICAZIONE ----------------------
        AuthHandler roleCheck = new RoleCheckHandler(marketplace);
        AuthHandler credentialCheck = new CredentialCheckHandler(marketplace);
        roleCheck.linkWith(credentialCheck);
        AuthService authService = new AuthService(roleCheck);

        if (authService.logIn("venditore1", "passVenditore")) {
            System.out.println("[Test] Login venditore riuscito");
        } else {
            throw new RuntimeException("Autenticazione fallita per il venditore");
        }

        // ---------------------- CREAZIONE E PUBBLICAZIONE PRODOTTO ----------------------
        FileInformazioniProdotto infoProdotto = new FileInformazioniProdotto(
                "Mele Golden", azienda
        );
        gestoreCreazioni.creaProdotto(infoProdotto, venditore);

        // Recupero prodotto dal marketplace
        Prodotto prodotto = marketplace.getProdotti().values().stream()
                .filter(p -> p.getNome().equals("Mele Golden"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

        // Attiva la vendita
        venditore.attivaLaVendita(prodotto, BigDecimal.valueOf(2.5), 50);

        // ---------------------- RICHIESTA SPONSORIZZAZIONE ----------------------
        SistemaSponsorizzazioni sistemaSponsorizzazioni = new SistemaSponsorizzazioni();
        EmailSystem emailSystem = new EmailSystem();
        GestoreSponsorizzazioni gestoreSponsorizzazioni = new GestoreSponsorizzazioni(sistemaSponsorizzazioni, emailSystem, marketplace);

        // Consideriamo il prodotto come messaggio
        Prodotto messaggioProdotto =  prodotto; // assumendo Prodotto implementa Messaggio

        // Richiesta sponsorizzazione
        venditore.richiediSponsorizzazione(gestoreSponsorizzazioni,  messaggioProdotto, Piattaforme.INSTAGRAM);

        // ---------------------- CONTROLLO ----------------------
        System.out.println("Sponsorizzazioni registrate:");
        for (IElemento m : sistemaSponsorizzazioni.getSponsorizzazioni()) {
            System.out.println("- " + m.getNome());
        }

        // Logout venditore
        Session.logout();

        // Salvataggio dati
        try {
            persistenceManager.salva();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}