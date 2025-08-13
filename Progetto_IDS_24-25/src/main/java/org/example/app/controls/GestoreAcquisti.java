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
        public static void testAcquisto() {
            // 1. Creazione sistema di pagamento e marketplace
            SistemaPagamenti sistemaPagamenti = new SistemaPagamenti();
            Marketplace marketplace = new Marketplace();

            // 2. Creazione gestore acquisti
            GestoreAcquisti gestoreAcquisti = new GestoreAcquisti(sistemaPagamenti, marketplace);

            // 3. Creazione attore acquirente
            Componente acquirente = new Componente(
                    new Account("acquirente1", "pass", tipoAccount.GENERICO),
                    999,
                    "ACQUIRENTE@GMAIL.COM"
            );

            // 4. Creazione prodotto e pacchetto
            Azienda aziendaFrutta = new Azienda("AziendaFrutta", "codF1");
            Produttore produttore = new Produttore(
                    new Account("produttore1", "pass", tipoAccount.PRODUTTORE),
                    100,
                    "PRODUTTORE@GMAIL.COM",
                    aziendaFrutta
            );

            Prodotto prodotto = new ProdottoBase("Mele Golden", aziendaFrutta, produttore);
            prodotto.setPrezzo(BigDecimal.valueOf(2.5)); // prezzo unitario
            prodotto.setVendita(true); // disponibile alla vendita
            marketplace.aggiungiProdotto(prodotto);

            Pacchetto pacchetto = new Pacchetto("Pacchetto Degustazione");
            pacchetto.getProdotti().add(prodotto);
            marketplace.aggiungiPacchetto(pacchetto);

            // 5. Test acquisto prodotto
            System.out.println(">>> Test acquisto prodotto");
            gestoreAcquisti.acquistaProdotto(acquirente, prodotto, 3);

            // 6. Test acquisto pacchetto
            System.out.println(">>> Test acquisto pacchetto");
            gestoreAcquisti.acquistaPacchetto(acquirente, pacchetto);
        }

    }

