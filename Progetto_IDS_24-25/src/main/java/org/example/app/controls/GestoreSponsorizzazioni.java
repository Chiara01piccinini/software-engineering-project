package org.example.app.controls;

import org.example.app.model.Marketplace;
import org.example.app.model.Messaggio;
import org.example.app.model.Piattaforme;
import org.example.app.model.Venditore;
import org.example.app.view.EmailSystem;
import org.example.app.view.SistemaSponsorizzazioni;

public class GestoreSponsorizzazioni {
    private SistemaSponsorizzazioni sistema;
    private EmailSystem email;
    private Marketplace ecommerce;

    public GestoreSponsorizzazioni(SistemaSponsorizzazioni sistema, EmailSystem email, Marketplace ecommerce) {
        this.sistema = sistema;
        this.email = email;
        this.ecommerce = ecommerce;
    }

    public GestoreSponsorizzazioni(SistemaSponsorizzazioni sistema, EmailSystem email) {
        this.sistema = sistema;
        this.email = email;
    }

    public SistemaSponsorizzazioni getSistema() {
        return sistema;
    }

    public void setSistema(SistemaSponsorizzazioni sistema) {
        this.sistema = sistema;
    }

    public EmailSystem getEmail() {
        return email;
    }

    public void setEmail(EmailSystem email) {
        this.email = email;
    }
    public void pubbliacaContenuto(Venditore venditore, Messaggio messaggio, Piattaforme piattaforma){
        if (ecommerce.getAccount().containsKey(venditore.getAccount().getId())){
            System.out.println("[GestoreSponsorizzazioni] Richiesta ricevuta dal venditore: " + venditore.getNome());
            sistema.pubblica(messaggio,piattaforma);
            email.inviaMail(venditore.getEmail(),"contenuto pubblicato","il prodotto" + messaggio.getContenuto() +
                    " è stato pubblicato su " +  piattaforma);
        }
    }
}
