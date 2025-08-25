package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.model.Prodotto;
import org.example.app.view.SistemaPagamenti;

import java.math.BigDecimal;

    public class GestoreAcquisti {

        private SistemaPagamenti sistemaPagamenti;
        private Marketplace marketplace;

        public GestoreAcquisti(SistemaPagamenti sistemaPagamenti,Marketplace marketplace) {
            this.marketplace=marketplace;
            this.sistemaPagamenti = sistemaPagamenti;
        }

        public void acquistaProdotto(Componente componente, Prodotto prodotto, int quantità) {
            if(prodotto.getVendita()== true){
            BigDecimal totale = prodotto.calcolaPrezzo().multiply(BigDecimal.valueOf(quantità));
            boolean esito = sistemaPagamenti.eseguiPagamento(componente, totale);

            if (esito) {
                componente.riceviMessaggio("Pagamento effettuato con successo per " + quantità + "x " + prodotto.getNome());
                for(int i=0 ; i == quantità ;i++){
                    marketplace.rimuoviProdotto(prodotto.getId());
                }
            } else {
                componente.riceviMessaggio("Pagamento fallito per " + prodotto.getNome());
            }
            }
        }

        public void acquistaPacchetto(Componente componente, Pacchetto pacchetto) {
            BigDecimal totale = pacchetto.calcolaPrezzo();
            boolean esito = sistemaPagamenti.eseguiPagamento(componente, totale);

            if (esito) {
                componente.riceviMessaggio("Pagamento effettuato con successo per pacchetto: " + pacchetto.getNome());
                    marketplace.rimuoviPacchetto(pacchetto.getId());
            } else {
                componente.riceviMessaggio("Pagamento fallito per pacchetto: " + pacchetto.getNome());
            }
        }


    }

