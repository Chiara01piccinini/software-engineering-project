package org.example.software_engineering_project.model;

import org.example.software_engineering_project.controls.*;
import org.example.software_engineering_project.view.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;

@Component
@RequestScope
public abstract class Venditore extends Componente {

    private Azienda azienda;

    // Costruttore con Marketplace e Session iniettati
    public Venditore(Account account, int matricola, String email, Azienda azienda,
                     Marketplace sistema, Session session) {
        super(matricola, email, sistema, session);
        this.setAccount(account);
        this.azienda = azienda;
    }

    public Azienda getAzienda() { return azienda; }
    public void setAzienda(Azienda azienda) { this.azienda = azienda; }

    public void inviaFile(GestorePubblicazioni gestore, Messaggio info) {
        gestore.inviaInformazioni(this, info);
    }

    public boolean attivaLaVendita(Prodotto p, BigDecimal prezzo, int quantita) {
        if (p != null && prezzo.compareTo(BigDecimal.ZERO) > 0 && quantita > 0) {
            p.setPrezzo(prezzo);
            p.setVendita(true);
            p.setQuantita(quantita);
            return true;
        }
        return false;
    }

    public void richiediSponsorizzazione(GestoreSponsorizzazioni gestore,
                                         IElemento messaggio,
                                         Piattaforme tipo) {
        if (!super.session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }

        if (super.session.getCurrentUser().getTipologia() == tipoAccount.GENERICO ||
                super.session.getCurrentUser().getTipologia() == tipoAccount.ANIMATORE) {
            throw new SecurityException("Operazione non consentita: ruolo non autorizzato");
        }
        gestore.pubblicaContenuto(this, messaggio, tipo);
    }

    public void richiediModificaDisponibilità(GestoreCreazioni gestore, Prodotto prodotto, int nq) {
        if (!super.session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }

        if (super.session.getCurrentUser().getTipologia() == tipoAccount.GENERICO ||
                super.session.getCurrentUser().getTipologia() == tipoAccount.ANIMATORE) {
            throw new SecurityException("Operazione non consentita: ruolo non autorizzato");
        }

        if (this.azienda == prodotto.getAzienda()) {
            gestore.modificaDisponibilità(prodotto, nq);
        }
    }

    public void modificaPrezzo(Prodotto prodotto, BigDecimal np) {
        if (!super.session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }

        if (super.session.getCurrentUser().getTipologia() == tipoAccount.GENERICO ||
                super.session.getCurrentUser().getTipologia() == tipoAccount.ANIMATORE) {
            throw new SecurityException("Operazione non consentita: ruolo non autorizzato");
        }

        if (prodotto.getAzienda() == this.azienda && prodotto.getVendita()) {
            prodotto.setPrezzo(np);
        }
    }
}
