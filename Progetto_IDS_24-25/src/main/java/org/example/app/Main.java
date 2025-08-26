package org.example.app;

import org.example.app.controls.*;
import org.example.app.model.*;
import org.example.app.view.EmailSystem;
import org.example.app.view.SistemaPagamenti;
import org.example.app.view.AuthService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        // Puoi commentare uno dei test senza problemi
        testLoginEAutenticazione();
        testCreazioneAccount();
        //testCreazionePacchetto();
        //testAcquisto();
        testPrenotazioneEvento();
    }

    // ---------------------- TEST AUTENTICAZIONE ----------------------
    public static void testLoginEAutenticazione() {
        System.out.println(">>> Test autenticazione");

        Marketplace marketplace = new Marketplace(); // istanza locale
        Account account = new Account("mario", "password123", tipoAccount.GENERICO);
        marketplace.getAccount().put(account.getId(), account);

        AuthHandler handler = new CredentialCheckHandler(marketplace);
        handler.linkWith(new RoleCheckHandler(marketplace));

        AuthService authService = new AuthService(handler);

        boolean loginOk = authService.logIn("mario", "password123");
        System.out.println(loginOk ? "[Auth] Login avvenuto!" : "[Auth] Login fallito!");
        System.out.println();
    }

    // ---------------------- TEST CREAZIONE ACCOUNT ----------------------
    public static void testCreazioneAccount() {
        System.out.println(">>> Test creazione account");

        Marketplace marketplace = new Marketplace();
        Curatore curatore = new Curatore(
                new Account("curatore1", "pass", tipoAccount.GENERICO),
                1,
                "curatore_fake@domain.com"
        );

        GestorePubblicazioni gestorePubblicazioni = new GestorePubblicazioni(curatore, new EmailSystem());
        GestoreCreazioni gestoreCreazioni = new GestoreCreazioni(gestorePubblicazioni, curatore);
        gestorePubblicazioni.setGestoreCreazioni(gestoreCreazioni);

        FileInformazioniAccount info = new FileInformazioniAccount(
                "utente1",
                "password123",
                "utente1_fake@domain.com"
        );

        Azienda aziendaProduttore = new Azienda("Azienda Mario", "cod123");

        Produttore sender = new Produttore(
                new Account("produttore1", "pass", tipoAccount.PRODUTTORE),
                1,
                "produttore_fake@domain.com",
                aziendaProduttore
        );

        gestoreCreazioni.creaAccount(info, sender);

        marketplace.getAccount().forEach((id, a) ->
                System.out.println("ID: " + id + ", Username: " + a.getNomeUtente() + ", Tipo: " + a.getTipologia())
        );
        System.out.println();
    }

    // ---------------------- TEST CREAZIONE PACCHETTO ----------------------
    public static void testCreazionePacchetto() {
        System.out.println(">>> Test creazione pacchetto");

        Marketplace marketplace = new Marketplace();
        Curatore curatore = new Curatore(
                new Account("curatore1", "pass", tipoAccount.GENERICO),
                1,
                "curatore_fake@domain.com"
        );

        GestorePubblicazioni gestorePubblicazioni = new GestorePubblicazioni(curatore, new EmailSystem());

        Produttore produttore = new Produttore(
                new Account("prod1", "pass", tipoAccount.PRODUTTORE),
                1,
                "prod1_fake@domain.com",
                new Azienda("Frutta", "cod1")
        );

        Trasformatore trasformatore = new Trasformatore(
                new Account("tras1", "pass", tipoAccount.TRASFORMATORE),
                2,
                "tras1_fake@domain.com",
                new Azienda("Salumi", "cod2")
        );

        Set<Prodotto> prodotti = new HashSet<>();
        prodotti.add(new ProdottoBase("Pere", produttore.getAzienda(), produttore));
        prodotti.add(new ProdottoElaborato("Salame Toscano", trasformatore.getAzienda(), trasformatore));

        FileInformazioniPacchetto pacchettoInfo = new FileInformazioniPacchetto(
                "Degustazione Tipica",
                BigDecimal.valueOf(10),
                prodotti,
                5
        );

        gestorePubblicazioni.inviaPacchetto(produttore, pacchettoInfo);
        System.out.println();
    }

    public static void testAcquisto() {
        System.out.println(">>> Test acquisto carrello");

        // 1. Marketplace locale per il test
        Marketplace marketplace = new Marketplace();

        // 2. Produttore e prodotto
        Produttore produttore = new Produttore(
                new Account("prod2", "pass", tipoAccount.PRODUTTORE),
                2,
                "prod2_fake@domain.com",
                new Azienda("Frutta2", "cod3")
        );
        Prodotto prodotto = new ProdottoBase("Mele Golden", produttore.getAzienda(), produttore);
        prodotto.setPrezzo(BigDecimal.valueOf(2.5));
        prodotto.setVendita(true);
        marketplace.aggiungiProdotto(prodotto);

        // 3. Pacchetto
        Pacchetto pacchetto = new Pacchetto(
                "Pacchetto Degustazione",
                BigDecimal.valueOf(10),
                Set.of(prodotto),
                10
        );
        pacchetto.setPrezzo(BigDecimal.valueOf(5.0));
        marketplace.aggiungiPacchetto(pacchetto);

        // 4. Gestore acquisti collegato allo stesso marketplace
        GestoreAcquisti gestoreAcquisti = new GestoreAcquisti(new SistemaPagamenti(), marketplace);

        // 5. Acquirente
        Componente acquirente = new Componente(
                new Account("acquirente1", "pass", tipoAccount.GENERICO),
                1,
                "acquirente_fake@domain.com"
        );

        // 6. Aggiunta prodotti/pacchetto al carrello
        acquirente.acquista(gestoreAcquisti, prodotto, 3);
        acquirente.acquista(gestoreAcquisti, pacchetto, 2);

        // 7. Mostra contenuto carrello
        System.out.println("Contenuto carrello:");
        acquirente.getAccount().getCarrello().forEach((el, q) ->
                System.out.println("- " + el.getNome() + " x" + q + " (Prezzo unitario: " + el.calcolaPrezzo() + ")")
        );

        // 8. Acquisto carrello
        gestoreAcquisti.acquistaCarrello(acquirente);
        System.out.println(">>> Acquisto completato!\n");
    }

    // ---------------------- TEST PRENOTAZIONE EVENTO ----------------------
    public static void testPrenotazioneEvento() {
        System.out.println(">>> Test prenotazione evento");

        Marketplace marketplace = new Marketplace();

        Evento evento = new Evento(
                new Date(),
                LocalDateTime.now().plusDays(1),
                new Position("luogo evento", 45.4642, 9.1900),
                "Concerto Rock",
                100
        );

        marketplace.aggiungiEvento(evento);
        evento.rimuoviBiglietto(3);

        System.out.println("Biglietti iniziali: 100");
        System.out.println("Biglietti rimasti: " + evento.getBiglietti());
        System.out.println();
    }
}

