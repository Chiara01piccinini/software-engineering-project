package org.example.app.view;

import org.example.app.model.Componente;
import org.example.app.model.IElemento;
import org.example.app.model.Pacchetto;
import org.example.app.model.Prodotto;

import java.math.BigDecimal;

public class SistemaPagamenti {
    public boolean eseguiPagamento(Componente acquirente, IElemento elemento, BigDecimal importo) {
        String nomeAzienda = "";
        if (elemento instanceof Prodotto) {
            nomeAzienda = ((Prodotto) elemento).getAzienda().getName();
        } else if (elemento instanceof Pacchetto) {
            nomeAzienda =  ((Pacchetto) elemento).getProdotti().iterator().next().getAzienda().getName();
        }

        System.out.println("[SistemaPagamenti] Inizio pagamento per l'azienda: " + nomeAzienda);
        System.out.println("[SistemaPagamenti] Importo da pagare: " + importo + " EUR");

        return true; // simulazione pagamento
    }
}



