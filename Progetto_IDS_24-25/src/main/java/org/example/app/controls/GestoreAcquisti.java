package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.model.Prodotto;
import org.example.app.view.SistemaPagamenti;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class GestoreAcquisti {

    private SistemaPagamenti sistemaPagamenti;
    private Marketplace marketplace;

    public GestoreAcquisti(SistemaPagamenti sistemaPagamenti, Marketplace marketplace) {
        this.marketplace = marketplace;
        this.sistemaPagamenti = sistemaPagamenti;
    }

    public void acquistaProdotto(Componente componente, Prodotto prodotto, int quantità) {
        if (!prodotto.getVendita()) {
            componente.riceviMessaggio("Prodotto non in vendita: " + prodotto.getNome());
            return;
        }
        if (quantità > prodotto.getQuantita()) {
            componente.riceviMessaggio("Quantità richiesta non disponibile per: " + prodotto.getNome());
            return;
        }
        BigDecimal totale = prodotto.calcolaPrezzo().multiply(BigDecimal.valueOf(quantità));
        boolean esito = sistemaPagamenti.eseguiPagamento(componente, prodotto, totale);

        if (esito) {
            componente.riceviMessaggio("Pagamento effettuato con successo per "
                    + quantità + "x " + prodotto.getNome());

            prodotto.setQuantita(prodotto.getQuantita() - quantità);
            if (prodotto.getQuantita() <= 0) {
                marketplace.rimuoviProdotto(prodotto.getId());
            }
        } else {
            componente.riceviMessaggio("Pagamento fallito per " + prodotto.getNome());
        }
    }

    public void acquistaPacchetto(Componente componente, Pacchetto pacchetto, int quantità) {
        if (quantità > pacchetto.getQuantita()) {
            componente.riceviMessaggio("Quantità richiesta non disponibile per il pacchetto: " + pacchetto.getNome());
            return;
        }

        BigDecimal totale = pacchetto.calcolaPrezzo().multiply(BigDecimal.valueOf(quantità));
        boolean esito = sistemaPagamenti.eseguiPagamento(componente, pacchetto, totale);

        if (esito) {
            componente.riceviMessaggio("Pagamento effettuato con successo per "
                    + quantità + "x Pacchetto: " + pacchetto.getNome());

            pacchetto.setQuantita(pacchetto.getQuantita() - quantità);
            if (pacchetto.getQuantita() <= 0) {
                marketplace.rimuoviPacchetto(pacchetto.getId());
            }
        } else {
            componente.riceviMessaggio("Pagamento fallito per pacchetto: " + pacchetto.getNome());
        }
    }

    public void acquistaCarrello(Componente componente) {
        BigDecimal totale = BigDecimal.ZERO;

        // Verifica disponibilità prima di procedere
            for (Map.Entry<UUID, Integer> entry : componente.getAccount().getCarrello().entrySet()) {
            UUID id = entry.getKey();
            int quantita = entry.getValue();

            IElemento item = Marketplace.getElementoById(id);
            if (item instanceof Prodotto p && quantita > p.getQuantita()) {
                componente.riceviMessaggio("Quantità richiesta non disponibile per: " + p.getNome());
                return;
            } else if (item instanceof Pacchetto pac && quantita > pac.getQuantita()) {
                componente.riceviMessaggio("Quantità richiesta non disponibile per il pacchetto: " + pac.getNome());
                return;
            }

            totale = totale.add(item.calcolaPrezzo().multiply(BigDecimal.valueOf(quantita)));
        }

        boolean tuttiPagamentiRiusciti = true;

        // Pagamento singolo per ogni elemento
        for (Map.Entry<UUID, Integer> entry : componente.getAccount().getCarrello().entrySet()) {
            UUID id = entry.getKey();
            int quantita = entry.getValue();
            IElemento item = Marketplace.getElementoById(id);
            BigDecimal importo = item.calcolaPrezzo().multiply(BigDecimal.valueOf(quantita));

            boolean esito = sistemaPagamenti.eseguiPagamento(componente, item, importo);
            if (!esito) {
                tuttiPagamentiRiusciti = false;
                System.out.println("[Messaggio]: Pagamento fallito per " + item);
            }
        }

        if (tuttiPagamentiRiusciti) {
            componente.riceviMessaggio("Pagamento carrello completato. Totale speso: " + totale + " EUR");

            for (Map.Entry<UUID, Integer> entry : componente.getAccount().getCarrello().entrySet()) {
                UUID id = entry.getKey();
                int quantita = entry.getValue();
                IElemento item = Marketplace.getElementoById(id);

                if (item instanceof Prodotto p) {
                    p.setQuantita(p.getQuantita() - quantita);
                    if (p.getQuantita() <= 0) {
                        marketplace.rimuoviProdotto(p.getId());
                    }
                } else if (item instanceof Pacchetto pac) {
                    pac.setQuantita(pac.getQuantita() - quantita);
                    if (pac.getQuantita() <= 0) {
                        marketplace.rimuoviPacchetto(pac.getId());
                    }
                }
            }

            componente.getAccount().getCarrello().clear();
        } else {
            componente.riceviMessaggio("Pagamento carrello fallito. Nessun addebito effettuato.");
        }
    }

}
