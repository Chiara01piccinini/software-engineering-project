package org.example.app.view;

import org.example.app.model.Componente;
import java.math.BigDecimal;

public class SistemaPagamenti {
    public boolean eseguiPagamento(Componente componente, BigDecimal importo) {
        System.out.println("[SistemaPagamenti] Inizio pagamento per il venditore: " + componente.getNome());
        System.out.println("[SistemaPagamenti] Importo da pagare: " + importo + " EUR");

        // Simulazione della chiamata a un sistema di pagamento esterno
        // Qui restituiamo sempre true per semplicità
        boolean pagamentoRiuscito = true;

        if (pagamentoRiuscito) {
            System.out.println("[SistemaPagamenti] Pagamento completato con successo.");
        } else {
            System.out.println("[SistemaPagamenti] Pagamento fallito.");
        }

        return pagamentoRiuscito;
    }
}



