package org.example.app.view;

import org.example.app.model.Messaggio;
import org.example.app.model.Piattaforme;

import java.util.ArrayList;
import java.util.List;

public class SistemaSponsorizzazioni {
    private List<Messaggio> sponsorizzazioni = new ArrayList<>();
    public void pubblica(Messaggio s, Piattaforme piattaforma){
        sponsorizzazioni.add(s);
        System.out.println("[SistemaSponsorizzazioni] Sponsorizzazione pubblicata: " + s.getContenuto() + "piattaforma:" + piattaforma);
    }

    public List<Messaggio> getSponsorizzazioni() {
        return sponsorizzazioni;
    }

    public void setSponsorizzazioni(List<Messaggio> sponsorizzazioni) {
        this.sponsorizzazioni = sponsorizzazioni;
    }

}
