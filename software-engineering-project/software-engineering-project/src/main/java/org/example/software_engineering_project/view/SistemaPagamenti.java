package org.example.software_engineering_project.view;



import org.example.software_engineering_project.model.Componente;
import org.example.software_engineering_project.model.IElemento;
import org.example.software_engineering_project.model.Pacchetto;
import org.example.software_engineering_project.model.Prodotto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SistemaPagamenti {

    public boolean eseguiPagamento(Componente acquirente, IElemento elemento, BigDecimal importo) {
        String nomeAzienda = "";

        if (elemento instanceof Prodotto) {
            nomeAzienda = ((Prodotto) elemento).getAzienda().getName();
        } else if (elemento instanceof Pacchetto) {
            // prende il nome della prima azienda dei prodotti del pacchetto
            nomeAzienda = ((Pacchetto) elemento).getProdotti()
                    .iterator()
                    .next()
                    .getAzienda()
                    .getName();
        }

        System.out.println("[SistemaPagamenti] Inizio pagamento per l'azienda: " + nomeAzienda);
        System.out.println("[SistemaPagamenti] Importo da pagare: " + importo + " EUR");

        return true; // simulazione pagamento
    }
}

