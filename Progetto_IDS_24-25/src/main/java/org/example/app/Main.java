package org.example.app;

import org.example.app.controls.GestorePubblicazioni;
import org.example.app.model.*;
import org.example.app.view.EmailSystem;

import javax.tools.JavaFileManager;
import javax.xml.stream.Location;
import java.io.IOException;

public class Main {
public static void main(String[] args) {
    Prodotto prodotto = new Prodotto(1, "Scarpa X", new Azienda("Nike", "nvr"));
    Curatore curatore = new Curatore("Mario", "Rossi", 123, "mrzpccnn@gmail.com", null);
    Venditore venditore = new Produttore("Luca", "Bianchi", 456, "venditore@gmail.com", new Azienda("barbagianni", "hdf"), null);

    EmailSystem emailSystem = new EmailSystem();
    GestorePubblicazioni gestore = new GestorePubblicazioni(curatore, emailSystem, prodotto);

    FileInformazioniTestuale descrizione = new FileInformazioniTestuale("Descrizione bellissima scarpa");

    System.out.println("==> Invio descrizione per approvazione");
    gestore.inviaInformazioni(venditore, descrizione);

    if (prodotto.getDescrizione() != null) {
        System.out.println(" Descrizione prodotto approvata: " + prodotto.getDescrizione().getContenuto());
    } else {
        System.out.println(" Descrizione prodotto NON approvata.");
    }
}

}
