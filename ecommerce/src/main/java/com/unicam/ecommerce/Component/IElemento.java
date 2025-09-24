package com.unicam.ecommerce.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Interfaccia che rappresenta un elemento del sistema eCommerce.
 *
 * Fa parte dell'implementazione del Design Pattern Composite:
 * - Le classi "foglia" (es. {@link Prodotto}, {@link ProdottoTrasformato})
 *   implementano direttamente i metodi definiti qui.
 * - Le classi "composite" (es. {@link Pacchetto}) aggregano più elementi
 *   e forniscono una logica di calcolo che combina i figli.
 *
 */
public interface IElemento {

    /**
     * Restituisce il nome dell'elemento.
     * @return nome dell'elemento
     */
    String getNome();

    /**
     * Restituisce l'identificatore univoco dell'elemento.
     * @return UUID dell'elemento
     */
    UUID getId();

    /**
     * Calcola e restituisce il prezzo totale dell'elemento.
     * Per le foglie corrisponde al prezzo unitario * quantità,
     * per le composite è la somma dei prezzi dei sotto-elementi.
     *
     * @return prezzo come BigDecimal
     */
    BigDecimal calcolaPrezzo();

    /**
     * Imposta la quantità dell'elemento.
     * Nelle foglie rappresenta la quantità acquistata o disponibile,
     * nelle composite può non essere rilevante.
     *
     * @param i nuova quantità
     */
    void setQuantita(int i);

    /**
     * Restituisce la quantità corrente dell'elemento.
     * @return quantità disponibile o selezionata
     */
    int getQuantita();
}
