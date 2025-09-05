package org.example.software_engineering_project;

import org.example.software_engineering_project.controls.*;
import org.example.software_engineering_project.model.*;
import org.example.software_engineering_project.model.JsonPersistence.PersistenceManager;
import org.example.software_engineering_project.view.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class TestRunner {

    private final GestoreCreazioni gestoreCreazioni;
    private final GestorePubblicazioni gestorePubblicazioni;
    private final Marketplace marketplace;
    private final PersistenceManager persistenceManager;
    private final Session session;
    private final AuthService authService;

    public TestRunner(GestoreCreazioni gestoreCreazioni,
                      GestorePubblicazioni gestorePubblicazioni,
                      Marketplace marketplace,
                      PersistenceManager persistenceManager,
                      Session session,
                      AuthService authService) {
        this.gestoreCreazioni = gestoreCreazioni;
        this.gestorePubblicazioni = gestorePubblicazioni;
        this.marketplace = marketplace;
        this.persistenceManager = persistenceManager;
        this.session = session;
        this.authService = authService;
    }

    // ---------------------- Test creazione account e autenticazione ----------------------
    public void testCreazioneAccounteautenticazione() {
        System.out.println(">>> Test creazione account");

        Componente sender = new Componente(1, "mittente@email.com", marketplace, session);
        FileInformazioniAccount info = new FileInformazioniAccount("mario", "password123", "mario@email.com", tipoAccount.GENERICO);

        gestoreCreazioni.creaAccount(info, sender);
        salvaPersistenza();

        marketplace.getAccount().forEach((id, account) ->
                System.out.println("ID: " + id + ", Username: " + account.getNomeUtente() + ", Tipo: " + account.getTipologia())
        );

        boolean success = authService.logIn("mario", "password123");
        System.out.println(success ? "[Test] Login riuscito" : "[Test] Login fallito");

        boolean fail = authService.logIn("mario", "wrongpass");
        if (!fail) System.out.println("[Test] Login errato gestito correttamente");

        if (session.isAuthenticated()) {
            System.out.println("[Test] Utente autenticato in sessione: " + session.getCurrentUser().getNomeUtente());
            session.logout();
        } else {
            System.out.println("[Test] Nessun utente in sessione");
        }

        System.out.println();
    }

    // ---------------------- Test creazione contenuti ----------------------
    public void testCreazioneContenuti() {
        System.out.println(">>> Test creazione contenuti START");

        try {
            // 1. Creazione account Curatore
            FileInformazioniAccount curatoreInfo = new FileInformazioniAccount(
                    "curatore1",
                    "pass",
                    "curatore@email.com",
                    tipoAccount.CURATORE
            );
            Curatore curatoreSender = new Curatore(
                    new Account(curatoreInfo.getNomeUtente(), curatoreInfo.getPassword(), tipoAccount.CURATORE),
                    0,
                    "curatore@email.com",
                    new EmailSystem(),
                    marketplace,
                    session
            );

            // Creo account tramite GestoreCreazioni
            gestoreCreazioni.creaAccount(curatoreInfo, curatoreSender);

            // Aggiorno sessione subito dopo creazione account
            session.setCurrentUser(curatoreSender.getAccount());

            // Salvataggio persistente
            persistenceManager.salva();

            // 2. Creazione Produttore e prodotti
            Produttore produttore = new Produttore(
                    new Account("prod1", "pass", tipoAccount.PRODUTTORE),
                    1,
                    "prod1@email.com",
                    new Azienda("Frutta", new Position("luogo azienda 1", 15.46, 9.10), "cod1"),
                    marketplace,
                    session
            );

            FileInformazioniProdotto prodotto1Info = new FileInformazioniProdotto("Pere Fresche", produttore.getAzienda());
            FileInformazioniProdotto prodotto2Info = new FileInformazioniProdotto("Mele Fresche", produttore.getAzienda());

            // Creo prodotti tramite GestoreCreazioni
            gestoreCreazioni.creaProdotto(prodotto1Info, produttore);
            gestoreCreazioni.creaProdotto(prodotto2Info, produttore);

            // Salvataggio persistente dopo prodotti
            persistenceManager.salva();

            // 3. Creazione Pacchetto
            Set<Prodotto> prodottiPacchetto = new HashSet<>();
            prodottiPacchetto.add(marketplace.getProdotti().get("Pere Fresche"));
            prodottiPacchetto.add(marketplace.getProdotti().get("Mele Fresche"));

            DistributoreDiTipicita distributore = new DistributoreDiTipicita(
                    new Account("dist1", "distpass", tipoAccount.DISTRIBUTOREDITIPICITA),
                    2,
                    "dist1@email.com",
                    new Azienda("DistribuzioniSRL", new Position("luogo azienda 2", 15.50, 9.15), "codDist"),
                    marketplace,
                    session
            );

            FileInformazioniPacchetto pacchettoInfo = new FileInformazioniPacchetto(
                    "Degustazione Tipica",
                    BigDecimal.valueOf(10),
                    prodottiPacchetto,
                    5
            );

            // Creo pacchetto tramite GestoreCreazioni
            gestoreCreazioni.creaPacchetto(pacchettoInfo, distributore);

            // Salvataggio persistente dopo pacchetto
            persistenceManager.salva();

            // 4. Creazione Evento
            // 1. Creazione Animatore
            Animatore animatore = new Animatore(
                    3,
                    "animatore@email.com",
                    marketplace,     // marketplace necessario
                    session,         // sessione corrente
                    new Account("anim1", "passAnim", tipoAccount.ANIMATORE)
            );

// 2. Creazione Evento
            FileInformazioniEvento eventoInfo = new FileInformazioniEvento(
                    LocalDate.now(),        // data dell'evento
                    LocalTime.now(),    // orario dell'evento
                    new Position("Piazza Centrale", 45.123, 9.456),
                    "Fiera del Gusto",
                    100,                    // numero biglietti
                    "Degustazioni e spettacoli"
            );

            gestoreCreazioni.creaEvento(eventoInfo, animatore);

            // Salvataggio persistente dopo evento
            persistenceManager.salva();

            System.out.println("[Test] Tutti i contenuti creati e salvati correttamente");

        } catch (Exception e) {
            System.err.println("[FATAL] Errore durante il test creazione contenuti");
            e.printStackTrace();
        } finally {
            session.logout();
        }

        System.out.println(">>> Test creazione contenuti END");
    }


    // ---------------------- Test acquisto ----------------------
    public void testAcquisto() {
        System.out.println(">>> Test acquisto START");

        try {
            // --- 1. Creazione account acquirente ---
            FileInformazioniAccount infoAcquirente = new FileInformazioniAccount(
                    "acquirente1",
                    "pass",
                    "acquirente@email.com",
                    tipoAccount.GENERICO
            );

            Componente acquirente = new Componente(1, "acquirente@email.com", marketplace, session);

            gestoreCreazioni.creaAccount(infoAcquirente, acquirente);
            salvaPersistenza();

            // Debug: stampa account nel marketplace
            System.out.println("[Debug] Account registrati nel marketplace:");
            marketplace.getAccount().forEach((id, account) ->
                    System.out.println(" ID: " + id +
                            " | Username: " + account.getNomeUtente() +
                            " | Tipo: " + account.getTipologia())
            );

            // --- 2. Login ---
            boolean loginSuccess = authService.logIn("acquirente1", "pass");

            // Recupera l'account creato dal marketplace
            Account acquirenteAccount = marketplace.getAccount().values().stream()
                    .filter(a -> a.getNomeUtente().equals("acquirente1"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Account non trovato"));

            // Imposta sessione
            session.setAuthenticated(true);
            session.setCurrentUser(acquirenteAccount);
            acquirente.setAccount(acquirenteAccount);

            System.out.println(loginSuccess ? "[Test] Login riuscito" : "[Test] Login fallito");
            System.out.println("[Test] Utente autenticato in sessione: " + acquirenteAccount.getNomeUtente());

            // --- 3. Creazione prodotti direttamente senza riferimento a Produttore ---
            Position luogoAzienda = new Position("Luogo Azienda", 45.0, 9.0);
            Azienda aziendaProdotti = new Azienda("Frutta2", luogoAzienda, "cod3");

            Produttore produttore = new Produttore(
                    new Account("prod2", "pass", tipoAccount.PRODUTTORE),
                    2,
                    "prod2@email.com",
                    new Azienda("Frutta2", luogoAzienda, "cod3"),
                    marketplace,
                    session
            );

            gestoreCreazioni.creaProdotto(new FileInformazioniProdotto("Mele Golden", aziendaProdotti), produttore);
            gestoreCreazioni.creaProdotto(new FileInformazioniProdotto("Banane", aziendaProdotti), produttore);

            gestoreCreazioni.creaProdotto(new FileInformazioniProdotto("Mele Golden", produttore.getAzienda()), produttore);
            gestoreCreazioni.creaProdotto(new FileInformazioniProdotto("Banane", produttore.getAzienda()), produttore);

// Recupera dal marketplace
            Prodotto prodotto1 = marketplace.getProdotti().values().stream()
                    .filter(p -> p.getNome().equals("Mele Golden"))
                    .findFirst().orElseThrow();
            Prodotto prodotto2 = marketplace.getProdotti().values().stream()
                    .filter(p -> p.getNome().equals("Banane"))
                    .findFirst().orElseThrow();

// Imposta prezzo e quantità
            prodotto1.setPrezzo(BigDecimal.valueOf(2.5));
            prodotto1.setQuantita(10);
            prodotto1.setVendita(true);

            prodotto2.setPrezzo(BigDecimal.valueOf(3.0));
            prodotto2.setQuantita(15);
            prodotto2.setVendita(true);


            // --- 4. Creazione distributore e pacchetto ---
            DistributoreDiTipicita distributore = new DistributoreDiTipicita(
                    new Account("dist1", "distpass", tipoAccount.DISTRIBUTOREDITIPICITA),
                    3,
                    "dist@email.com",
                    new Azienda("DistribuzioniSRL", luogoAzienda, "cod4"),
                    marketplace,
                    session
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

            gestoreCreazioni.creaPacchetto(pacchettoInfo, distributore);

            // --- 5. Debug prodotti/pacchetti ---
            System.out.println("[Debug] Prodotti nel marketplace:");
            marketplace.getProdotti().values().forEach(p ->
                    System.out.println(" - " + p.getNome()
                            + " | Prezzo: " + p.calcolaPrezzo()
                            + " | Quantità: " + p.getQuantita())
            );

            System.out.println("[Debug] Pacchetti nel marketplace:");
            marketplace.getPacchetti().values().forEach(p ->
                    System.out.println(" - " + p.getNome()
                            + " | Quantità: " + p.getQuantita()
                            + " | Prezzo calcolato: " + p.calcolaPrezzo())
            );

            salvaPersistenza();

            // --- 6. ACQUISTO da parte dell'acquirente ---
            System.out.println("[Test] Aggiunta prodotti/pacchetti al carrello...");

            acquirenteAccount.aggiungiElemento(prodotto1, 2); // 2 mele
            acquirenteAccount.aggiungiElemento(marketplace.getPacchetti().values().iterator().next(), 1); // 1 pacchetto

            System.out.println("[Debug] Carrello acquirente prima dell'acquisto: " + acquirenteAccount.getCarrello());

            // acquisto effettivo
            acquirente.acquistaCarrello(new GestoreAcquisti(new SistemaPagamenti(), marketplace));

            System.out.println("[Test] Acquisto completato per l'utente: " + acquirenteAccount.getNomeUtente());
            System.out.println("[Debug] Carrello dopo acquisto: " + acquirenteAccount.getCarrello());

            salvaPersistenza();

            // --- 7. Logout ---
            if (session.isAuthenticated()) {
                System.out.println("[Test] Logout utente: " + session.getCurrentUser().getNomeUtente());
                session.logout();
            }

        } catch (Exception e) {
            System.err.println("[FATAL] Errore durante il test acquisto");
            e.printStackTrace();
        }

        System.out.println(">>> Test acquisto END");
    }

    // ---------------------- Test prenotazione evento ----------------------
    public void testPrenotazioneEvento() {
        System.out.println(">>> Test prenotazione evento START");
        try {
            // --- 1. Creazione account utente ---
            Componente utente = new Componente(1, "utenteEvento@email.com", marketplace, session);
            FileInformazioniAccount info = new FileInformazioniAccount(
                    "utenteEvento",
                    "passEvento",
                    "utenteEvento@email.com",
                    tipoAccount.GENERICO
            );
            gestoreCreazioni.creaAccount(info, utente);
            salvaPersistenza();

            // --- 2. Login del componente tramite authService ---
            boolean loginSuccess = authService.logIn("utenteEvento", "passEvento");
            session.setCurrentUser(utente.getAccount());
            session.setAuthenticated(true);

            if (!loginSuccess) {
                System.err.println("[Test] Login fallito per utenteEvento");
                return;
            }
            // --- 3. Creazione evento ---
            LocalDate dataEvento = LocalDate.of(2027, 11, 23);
            LocalTime oraInizio = LocalTime.of(23, 0);

            FileInformazioniEvento infoEvento = new FileInformazioniEvento(
                    dataEvento,
                    oraInizio,   // LocalDateTime completo
                    new Position("luogo evento", 45.4642, 9.1900),
                    "Concerto Rock",
                    100,
                    "Musica live"
            );

            Animatore animatore = new Animatore(
                    1,
                    "animatore@email.com",
                    marketplace,
                    session,
                    new Account("anim1", "pass", tipoAccount.ANIMATORE)
            );
            gestoreCreazioni.creaEvento(infoEvento, animatore);
            salvaPersistenza();

            // --- 4. Prenotazione evento da parte dell'utente ---
            Evento evento = marketplace.getEventi().values().stream()
                    .filter(e -> e.getNome().equals("Concerto Rock"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Evento non trovato"));

            int postiDaPrenotare = 2;
            if (evento.getBiglietti() < postiDaPrenotare) {
                System.err.println("[Test] Non ci sono abbastanza posti disponibili per l'evento");
            } else {
                utente.prenotaEvento(evento, postiDaPrenotare);
            }

            // Debug: mostra posti prenotati e disponibili
            System.out.println("[Debug] Posti prenotati per l'evento: " + evento.getBiglietti());
            System.out.println("[Debug] Posti disponibili rimasti: " + evento.getBiglietti());

            salvaPersistenza();

        } catch (Exception e) {
            System.err.println("[FATAL] Errore durante il test prenotazione evento");
            e.printStackTrace();
        } finally {
            // --- Logout ---
            if (session.isAuthenticated()) {
                session.logout();
            }
        }
        System.out.println(">>> Test prenotazione evento END");
    }


    // ---------------------- Test sponsorizzazione prodotto ----------------------
    public void testSponsorizzazioneProdotto() {
        System.out.println(">>> Test sponsorizzazione prodotto START");
        try {
            Produttore venditore = new Produttore(
                    new Account("venditore1", "passVenditore", tipoAccount.PRODUTTORE),
                    1,
                    "venditore1@email.com",
                    new Azienda("FruttaStore", new Position("SedeFrutta", 45.4642, 9.1900), "cod1"),
                    marketplace,
                    session
            );

            gestoreCreazioni.creaAccount(
                    new FileInformazioniAccount("venditore1", "passVenditore", "venditore1@email.com", tipoAccount.PRODUTTORE),
                    venditore
            );
            // --- Login del produttore ---
            boolean loginSuccess = authService.logIn("venditore1", "passVenditore");
            if (!loginSuccess) {
                System.err.println("[Test] Login fallito per venditore1");
                return;
            }
            session.setCurrentUser(venditore.getAccount());
            session.setAuthenticated(true);
            System.out.println("[Test] Venditore autenticato in sessione: " + session.getCurrentUser().getNomeUtente());

            salvaPersistenza();

            authService.logIn("venditore1", "passVenditore");

            // Creazione prodotto e sponsorizzazione
            FileInformazioniProdotto infoProdotto = new FileInformazioniProdotto("Mele Golden", venditore.getAzienda());
            gestoreCreazioni.creaProdotto(infoProdotto, venditore);
            salvaPersistenza();

            Prodotto prodotto = marketplace.getProdotti().values().stream()
                    .filter(p -> p.getNome().equals("Mele Golden"))
                    .findFirst()
                    .orElseThrow();

            venditore.attivaLaVendita(prodotto, BigDecimal.valueOf(2.5), 50);

            SistemaSponsorizzazioni sistemaSponsorizzazioni = new SistemaSponsorizzazioni();
            GestoreSponsorizzazioni gestoreSponsorizzazioni = new GestoreSponsorizzazioni(
                    sistemaSponsorizzazioni,
                    new EmailSystem(),
                    marketplace
            );

            venditore.richiediSponsorizzazione(gestoreSponsorizzazioni, prodotto, Piattaforme.INSTAGRAM);


        } finally {
            session.logout();
        }
        System.out.println(">>> Test sponsorizzazione prodotto END");
    }

    // ---------------------- Metodo ausiliario per salvare persistenza ----------------------
    private void salvaPersistenza() {
        try {
            persistenceManager.salva();
        } catch (IOException e) {
            System.err.println("[Errore] Salvataggio persistenza fallito:");
            e.printStackTrace();
        }
    }
}

