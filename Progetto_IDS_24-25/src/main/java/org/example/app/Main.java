package org.example.app;

import org.example.app.controls.GestoreCreazioni;
import org.example.app.controls.GestorePubblicazioni;
import org.example.app.model.*;
import org.example.app.view.EmailSystem;

import javax.tools.JavaFileManager;
import javax.xml.stream.Location;
import java.io.IOException;

public class Main {
public static void main(String[] args) {
    Prodotto prodotto = new Prodotto( "Scarpa X", new Azienda("Nike", "nvr"));
    Curatore curatore = new Curatore("Mario", "Rossi", 123, "mrzpccnn@gmail.com", null);
    Venditore venditore = new Produttore("Luca", "Bianchi", 456, "venditore@gmail.com", new Azienda("barbagianni", "hdf"), null);
    Azienda azienda = new Azienda("Farabollini", "5vfkgjeji");

    ProdottoBase pb = new ProdottoBase("Rucola", azienda, (Produttore) venditore);
    System.out.println(pb.getId());
    EmailSystem emailSystem = new EmailSystem();
    GestorePubblicazioni gestore = new GestorePubblicazioni(curatore, emailSystem, prodotto);
    GestoreCreazioni gestoreCreazioni = new GestoreCreazioni(gestore, curatore);

    FileInformazioniTestuale descrizione = new FileInformazioniTestuale("Descrizione bellissima scarpa", prodotto);
    FileInformazioniProdotto nuovoProdotto = new FileInformazioniProdotto("Pomodoro", azienda);
    System.out.println("==> Invio descrizione per approvazione");
    gestoreCreazioni.inviaInformazioni(venditore, descrizione);
    gestoreCreazioni.inviaProdotto(venditore, nuovoProdotto);
    gestore.inviaInformazioni(venditore, descrizione);
    gestore.inviaProdotto(venditore,nuovoProdotto);

    if (prodotto.getDescrizione() != null) {
        System.out.println(" Descrizione prodotto approvata: " + prodotto.getDescrizione().getContenuto());
    } else {
        System.out.println(" Descrizione prodotto NON approvata.");
    }


}

}
