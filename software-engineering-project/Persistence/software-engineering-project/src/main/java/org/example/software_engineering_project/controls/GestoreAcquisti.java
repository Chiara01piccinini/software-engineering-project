package org.example.software_engineering_project.controls;

import org.example.software_engineering_project.model.*;
import org.example.software_engineering_project.model.Prodotto;
import org.example.software_engineering_project.view.SistemaPagamenti;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class GestoreAcquisti {

    private final SistemaPagamenti sistemaPagamenti;
    private final Marketplace marketplace;

    public GestoreAcquisti(SistemaPagamenti sistemaPagamenti, Marketplace marketplace) {
        this.sistemaPagamenti = sistemaPagamenti;
        this.marketplace = marketplace;
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

        // Verifica disponibilità
        for (Map.Entry<UUID, Integer> entry : componente.getAccount().getCarrello().entrySet()) {
            UUID id = entry.getKey();
            int quantita = entry.getValue();

            IElemento item = marketplace.getElementoById(id); // <--- usa il bean marketplace
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

        // Pagamento singolo
        for (Map.Entry<UUID, Integer> entry : componente.getAccount().getCarrello().entrySet()) {
            UUID id = entry.getKey();
            int quantita = entry.getValue();
            IElemento item = marketplace.getElementoById(id);
            BigDecimal importo = item.calcolaPrezzo().multiply(BigDecimal.valueOf(quantita));

            boolean esito = sistemaPagamenti.eseguiPagamento(componente, item, importo);
            if (!esito) {
                tuttiPagamentiRiusciti = false;
                System.out.println("[Messaggio]: Pagamento fallito per " + item);
            }
        }

        if (tuttiPagamentiRiusciti) {
            componente.riceviMessaggio("Pagamento carrello completato. Totale speso: " + totale + " EUR");

            // Aggiorna quantità e rimuovi elementi esauriti
            for (Map.Entry<UUID, Integer> entry : componente.getAccount().getCarrello().entrySet()) {
                UUID id = entry.getKey();
                int quantita = entry.getValue();
                IElemento item = marketplace.getElementoById(id);

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
