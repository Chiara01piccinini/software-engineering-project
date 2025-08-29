package org.example.app.view;

import org.example.app.model.IElemento;
import org.example.app.model.Messaggio;
import org.example.app.model.Piattaforme;

import java.util.ArrayList;
import java.util.List;

public class SistemaSponsorizzazioni {
    private List<IElemento> sponsorizzazioni = new ArrayList<>();
    public void pubblica(IElemento s, Piattaforme piattaforma){
        sponsorizzazioni.add(s);
        System.out.println("[SistemaSponsorizzazioni] Sponsorizzazione pubblicata per : " + s.getNome() + "piattaforma:" + piattaforma);
    }

    public List<IElemento> getSponsorizzazioni() {
        return sponsorizzazioni;
    }

    public void setSponsorizzazioni(List<IElemento> sponsorizzazioni) {
        this.sponsorizzazioni = sponsorizzazioni;
    }

}
