package com.unicam.ecommerce.Service;

import com.unicam.ecommerce.Component.Componente;
import com.unicam.ecommerce.Component.IElemento;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Servizio simulato per la gestione dei pagamenti nel marketplace.
 *
 * Questo servizio si occupa di "eseguire" il pagamento di un elemento da parte di un acquirente.
 * Attualmente la logica è simulata: ogni pagamento ritorna sempre true (pagamento riuscito).
 *
 * Funzionalità principali:
 * - Riceve un acquirente (Componente), l'elemento da acquistare (IElemento) e l'importo (BigDecimal);
 * - Stampa su console informazioni riguardanti l'acquirente e l'elemento acquistato;
 * - Restituisce true per indicare che il pagamento è stato completato con successo.
 *
 * Nota:
 * Questo servizio è pensato come simulazione e può essere esteso in futuro per integrare
 * veri sistemi di pagamento (es. PayPal, Stripe) e logiche di transazione.
 */
@Service
public class SistemaPagamenti {

    /**
     * Simula l'esecuzione di un pagamento.
     *
     * @param acquirente Componente che effettua l'acquisto
     * @param elemento   Elemento da acquistare
     * @param importo    Importo da pagare
     * @return true se il pagamento "va a buon fine" (sempre true in questa implementazione)
     */
    public static boolean eseguiPagamento(Componente acquirente, IElemento elemento, BigDecimal importo) {
        String nomeAcquirente = acquirente.getNome() + " " + acquirente.getCognome();
        System.out.println("[SistemaPagamenti] Acquirente: " + nomeAcquirente + ", sta comprando: " + elemento.getNome());
        return true; // simulazione pagamento riuscito
    }
}
