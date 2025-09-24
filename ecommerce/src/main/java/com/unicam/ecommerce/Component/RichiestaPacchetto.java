package com.unicam.ecommerce.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Classe che rappresenta una richiesta di creazione di un pacchetto di prodotti.
 * Estende la classe astratta Richiesta.
 * Contiene informazioni sui prodotti inclusi, la quantità richiesta e l'eventuale sconto.
 */
public class RichiestaPacchetto extends Richiesta {

    private Map<Integer, Prodotto> prodotti;   // Mappa dei prodotti inclusi nel pacchetto con quantità richiesta
    private BigDecimal percentualeSconto;      // Percentuale di sconto applicata al pacchetto
    private int quantita;                       // Quantità complessiva del pacchetto richiesta

    /**
     * Costruttore principale.
     *
     * @param nome               Nome del pacchetto richiesto
     * @param prodotti           Prodotti inclusi nel pacchetto con quantità per ciascun prodotto
     * @param percentualeSconto  Sconto percentuale applicato al pacchetto
     * @param quantita           Quantità complessiva del pacchetto richiesta
     * @param componente         Componente che genera la richiesta
     */
    public RichiestaPacchetto(String nome, Map<Integer, Prodotto> prodotti, BigDecimal percentualeSconto, int quantita, Componente componente) {
        super(nome, componente);
        this.prodotti = prodotti;
        this.percentualeSconto = percentualeSconto;
        this.quantita = quantita;
    }

    // -------------------------------
    // Getter e setter
    // -------------------------------
    public Map<Integer, Prodotto> getProdotti() {
        return prodotti;
    }

    public BigDecimal getPercentualeSconto() {
        return percentualeSconto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setProdotti(Map<Integer, Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public void setPercentualeSconto(BigDecimal percentualeSconto) {
        this.percentualeSconto = percentualeSconto;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
