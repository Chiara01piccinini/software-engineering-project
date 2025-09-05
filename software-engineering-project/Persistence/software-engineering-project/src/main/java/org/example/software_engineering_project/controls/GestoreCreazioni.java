package org.example.software_engineering_project.controls;

import org.example.software_engineering_project.model.*;
import org.example.software_engineering_project.view.MappaService;
import org.springframework.stereotype.Service;


public class GestoreCreazioni implements IGestore {
    private Curatore curatore;
    private GestorePubblicazioni gestorePubblicazioni;
    private MappaService mappa;
    private Marketplace sistema;

    public GestoreCreazioni(GestorePubblicazioni gestorePubblicazioni,
                            Curatore curatore,
                            Marketplace sistema) {
        this.gestorePubblicazioni = gestorePubblicazioni;
        this.curatore = curatore;
        this.mappa = new MappaService();
        this.sistema = sistema; // ✅ ora è valorizzato dal costruttore
    }

    public MappaService getMappa() {
        return mappa;
    }

    public void setMappa(MappaService mappa) {
        this.mappa = mappa;
    }

    public Marketplace getSistema() {
        return sistema;
    }

    public void setSistema(Marketplace sistema) {
        this.sistema = sistema;
    }

    public Curatore getCuratore() {
        return curatore;
    }

    public void setCuratore(Curatore curatore) {
        this.curatore = curatore;
    }

    public GestorePubblicazioni getGestorePubblicazioni() {
        return gestorePubblicazioni;
    }

    public void setGestorePubblicazioni(GestorePubblicazioni gestorePubblicazioni) {
        this.gestorePubblicazioni = gestorePubblicazioni;
    }

    public void inviaInformazioni(Componente sender, Messaggio event) {
        gestorePubblicazioni.inviaInformazioni(sender, event);
    }

    public void inviaProdotto(Componente sender, Messaggio event) {
        gestorePubblicazioni.inviaProdotto(sender, event);
    }

    @Override
    public void inviaPacchetto(Componente sender, Messaggio event) {
        gestorePubblicazioni.inviaPacchetto(sender, event);
    }

    public void inviaEvento(Animatore sender, Messaggio event) {
        gestorePubblicazioni.inviaEvento(sender, event);
    }

    // Metodo usato da GestorePubblicazioni dopo approvazione
    public void creaProdotto(FileInformazioniProdotto info, Componente sender) {
        boolean esiste = sistema.getProdotti().values().stream()
                .anyMatch(p -> p.getNome().equals(info.getNome()));
        if (esiste) {
            sender.riceviMessaggio("[Messaggio]: Prodotto già esistente: " + info.getNome());
            return;
        }

        if (sender instanceof Produttore produttore) {
            ProdottoBase prodottoBase = new ProdottoBase(info.getNome(), info.getAzienda(), produttore);
            sistema.aggiungiProdotto(prodottoBase);
        } else if (sender instanceof Trasformatore trasformatore) {
            ProdottoElaborato prodottoElaborato = new ProdottoElaborato(info.getNome(), info.getAzienda(), trasformatore);
            sistema.aggiungiProdotto(prodottoElaborato);
        }
        sender.riceviMessaggio("Prodotto creato: " + info.getNome());
    }

    public void creaPacchetto(FileInformazioniPacchetto info, Componente sender) {
        boolean esiste = sistema.getPacchetti().values().stream()
                .anyMatch(p -> p.getNome().equals(info.getNome()));
        if (esiste) {
            sender.riceviMessaggio("[Messaggio]: Pacchetto già esistente: " + info.getNome());
            return;
        }

        if (info.getProdotti().size() > 1) {
            for (int i = 0; i < info.getQuantita(); i++) {
                Pacchetto pacchetto = new Pacchetto(info.getNome(), info.getPercentualeSconto(),
                        info.getProdotti(), info.getQuantita());
                sistema.aggiungiPacchetto(pacchetto);
            }
            sender.riceviMessaggio("Pacchetto creato: " + info.getNome());
        }
    }

    public void creaInformazioni(Messaggio info, Componente sender) {
        if (info instanceof FileInformazioniImmagini immagini) {
            immagini.getProdotto().aggiungiInformazioni(immagini);
            sender.riceviMessaggio("Immagini aggiunte per il prodotto: " + immagini.getNome());
        } else if (info instanceof FileInformazioniTestuale testo) {
            testo.getProdotto().aggiungiInformazioni(testo);
            sender.riceviMessaggio("Testo aggiunto per il prodotto: " + testo.getNome());
        }
    }

    public void creaEvento(FileInformazioniEvento info, Animatore sender) {
        boolean esiste = sistema.getEventi().values().stream()
                .anyMatch(e -> e.getNome().equals(info.getNome()) && e.getData().equals(info.getData()));
        if (esiste) {
            sender.riceviMessaggio("[Messaggio]: Evento già esistente: " + info.getNome());
            return;
        }

        Evento evento = new Evento(
                info.getData(),
                info.getOrario(),
                info.getLuogo(),
                info.getNome(),
                info.getBiglietti(),
                info.getDescrizione()
        );
        sistema.aggiungiEvento(evento);
        mappa.getMappa().put(info.getLuogo(), info.getNome());
        sender.riceviMessaggio("Evento creato: " + evento.getNome());
    }

    public void creaAccount(FileInformazioniAccount info, Componente sender) {
        boolean esiste = sistema.getAccount().values().stream()
                .anyMatch(acc -> acc.getNomeUtente().equals(info.getNomeUtente()));
        if (esiste) {
            sender.riceviMessaggio("[Messaggio]: Account già esistente: " + info.getNomeUtente());
            return;
        }

        tipoAccount tipo;
        if (sender instanceof Produttore) {
            tipo = tipoAccount.PRODUTTORE;
            mappa.getMappa().put(
                    ((Produttore) sender).getAzienda().getPosition(),
                    ((Produttore) sender).getAzienda().getName()
            );
        } else if (sender instanceof Trasformatore) {
            tipo = tipoAccount.TRASFORMATORE;
            mappa.getMappa().put(
                    ((Trasformatore) sender).getAzienda().getPosition(),
                    ((Trasformatore) sender).getAzienda().getName()
            );
        } else if (sender instanceof DistributoreDiTipicita) {
            tipo = tipoAccount.DISTRIBUTOREDITIPICITA;
        } else if (sender instanceof Animatore) {
            tipo = tipoAccount.ANIMATORE;
        } else if (sender instanceof Curatore) {
            tipo = tipoAccount.CURATORE;
        } else {
            tipo = tipoAccount.GENERICO;
        }

        Account account = new Account(info.getNomeUtente(), info.getPassword(), tipo);
        sender.riceviMessaggio(
                "Account creato = username: " + info.getNomeUtente()
                        + " email: " + info.getEmail()
                        + " tipo: " + tipo
        );
        sistema.aggiungiAccount(account);
    }

    public void modificaDisponibilità(Prodotto prodotto, int nq) {
        prodotto.setQuantita(nq);
    }
}
