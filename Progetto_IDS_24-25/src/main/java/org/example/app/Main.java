package org.example.app;

import org.example.app.controls.*;
import org.example.app.model.*;
import org.example.app.view.EmailSystem;

public class
Main {
    public static void main(String[] args) {
        // Crea Curatore (riceverà la richiesta via email)
        Curatore curatore = new Curatore("Mario", "Rossi", 1234, "mrzpccnn@gmail.com");

        // Crea sistema email
        EmailSystem emailSystem = new EmailSystem();

        // Crea gestore pubblicazioni
        GestorePubblicazioni gestorePubblicazioni = new GestorePubblicazioni(curatore, emailSystem);

        // Crea gestore creazioni e collega con pubblicazioni
        GestoreCreazioni gestoreCreazioni = new GestoreCreazioni(gestorePubblicazioni,curatore);
        gestorePubblicazioni.setGestoreCreazioni(gestoreCreazioni);

        // Inizializza marketplace
        Marketplace marketplace = new Marketplace(gestoreCreazioni);

        // Crea azienda e venditore
        Azienda aziendaTest = new Azienda("aziendaTest", "1256789");
        Produttore venditore = new Produttore("Luca", "Bianchi", 123456, "luca.bianchi@example.com", aziendaTest);

        // Crea le informazioni prodotto (da approvare via email)
        FileInformazioniProdotto infoProdotto = new FileInformazioniProdotto("ProdottoTest", aziendaTest);

        // Invia il prodotto (partirà la mail, il sistema attenderà la risposta)
        venditore.inviaProdotto(gestorePubblicazioni, infoProdotto);

        // Stampa finale dopo possibile approvazione
        System.out.println("\n--- Prodotti nel Marketplace ---");
        Marketplace.getProdotti().values().forEach(p ->
                System.out.println("Prodotto: " + p.getNome() + ", Azienda: " + p.getAzienda()));
    }
}
