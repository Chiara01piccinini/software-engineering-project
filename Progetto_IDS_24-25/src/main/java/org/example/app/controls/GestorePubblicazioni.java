package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.view.EmailSystem;

public class GestorePubblicazioni implements IGestore {
    private Curatore curatore;
    private EmailSystem notifiche;
    private GestoreCreazioni gestoreCreazioni;

    public GestorePubblicazioni(Curatore curatore, EmailSystem notifiche) {
        this.curatore = curatore;
        this.notifiche = notifiche;
    }

    public void setGestoreCreazioni(GestoreCreazioni gestoreCreazioni) {
        this.gestoreCreazioni = gestoreCreazioni;
    }

    @Override
    public void inviaInformazioni(Componente sender, Messaggio event) {
        if (event instanceof FileInformazioniTestuale info) {
            try {
                    gestoreCreazioni.creaInformazioni(info, sender);
                    notifiche.inviaMail(sender.getEmail(), "Informazioni approvate",
                            "Le informazioni sono state aggiunte: " + info.getNome());

            } catch (SecurityException e) {
                notifiche.inviaMail(sender.getEmail(), "Richiesta rifiutata", e.getMessage());
            }
        }
    }

    @Override
    public void inviaProdotto(Componente sender, Messaggio event) {
        if (event instanceof FileInformazioniProdotto info) {
            try {
                if (curatore.approvaProdotto(info, (Venditore) sender)) {
                    gestoreCreazioni.creaProdotto(info, sender);
                    notifiche.inviaMail(sender.getEmail(), "Prodotto approvato",
                            "Il prodotto è stato pubblicato: " + info.getNome());
                } else {
                    notifiche.inviaMail(sender.getEmail(), "Prodotto rifiutato", "La richiesta è stata rifiutata");
                }
            } catch (SecurityException e) {
                notifiche.inviaMail(sender.getEmail(), "Prodotto rifiutato", e.getMessage());
            }
        }
    }

    @Override
    public void inviaPacchetto(Componente sender, Messaggio event) {
        if (event instanceof FileInformazioniPacchetto info) {
            try {
                if (curatore.approvaPacchetto(info, (DistributoreDiTipicita) sender)) {
                    gestoreCreazioni.creaPacchetto(info, sender);
                    notifiche.inviaMail(sender.getEmail(), "Pacchetto approvato",
                            "Il pacchetto è stato pubblicato: " + info.getNome());
                } else {
                    notifiche.inviaMail(sender.getEmail(), "Pacchetto rifiutato", "La richiesta è stata rifiutata");
                }
            } catch (SecurityException e) {
                notifiche.inviaMail(sender.getEmail(), "Pacchetto rifiutato", e.getMessage());
            }
        }
    }

    public void inviaEvento(Animatore sender, Messaggio event) {
        if (event instanceof FileInformazioniEvento info) {
            try {
                if (curatore.approvaEvento(info, sender)) {
                    gestoreCreazioni.creaEvento(info, sender);
                    notifiche.inviaMail(sender.getEmail(), "Evento approvato",
                            "L'evento è stato pubblicato: " + info.getNome());
                } else {
                    notifiche.inviaMail(sender.getEmail(), "Evento rifiutato", "La richiesta è stata rifiutata");
                }
            } catch (SecurityException e) {
                notifiche.inviaMail(sender.getEmail(), "Evento rifiutato", e.getMessage());
            }
        }
    }

    public void inviaAccount(Componente sender, Messaggio event) {
        if (event instanceof FileInformazioniAccount info) {
            try {
                if (curatore.approvaAccount(info, sender)) {
                    gestoreCreazioni.creaAccount(info, sender);
                    notifiche.inviaMail(sender.getEmail(), "Account approvato",
                            "L'account è stato creato: " + info.getContenuto());
                } else {
                    notifiche.inviaMail(sender.getEmail(), "Account rifiutato", "La richiesta è stata rifiutata");
                }
            } catch (SecurityException e) {
                notifiche.inviaMail(sender.getEmail(), "Account rifiutato", e.getMessage());
            }
        }
    }
}
