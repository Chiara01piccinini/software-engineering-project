package org.example.software_engineering_project.controls;

import org.example.software_engineering_project.model.*;
import org.example.software_engineering_project.view.EmailSystem;
import org.example.software_engineering_project.view.SistemaSponsorizzazioni;
import org.springframework.stereotype.Service;


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
    public void pubblicaContenuto(Venditore venditore, IElemento messaggio, Piattaforme piattaforma){
        if (ecommerce.getAccount().containsKey(venditore.getAccount().getId())){
            System.out.println("[GestoreSponsorizzazioni] Richiesta ricevuta dal venditore: " + venditore.getNome());
            sistema.pubblica(messaggio,piattaforma);
            email.inviaMail(venditore.getEmail(),"contenuto pubblicato","il prodotto" + messaggio.getNome() +
                    " è stato pubblicato su " +  piattaforma);
        }
    }
}
