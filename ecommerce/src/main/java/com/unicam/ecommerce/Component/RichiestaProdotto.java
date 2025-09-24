package com.unicam.ecommerce.Component;

/**
 * Classe che rappresenta una richiesta di creazione o aggiunta di un singolo prodotto.
 * Estende la classe astratta Richiesta.
 * Contiene informazioni sull'azienda produttrice e sulla quantità richiesta.
 */
public class RichiestaProdotto extends Richiesta {

    private Azienda azienda;  // L'azienda produttrice del prodotto richiesto
    private int quantita;     // Quantità di prodotto richiesta

    /**
     * Costruttore principale.
     *
     * @param nome      Nome del prodotto richiesto
     * @param azienda   Azienda produttrice del prodotto
     * @param quantita  Quantità richiesta del prodotto
     * @param componente Componente che genera la richiesta
     */
    public RichiestaProdotto(String nome, Azienda azienda, int quantita, Componente componente) {
        super(nome, componente);
        this.azienda = azienda;
        this.quantita = quantita;
    }

    // -------------------------------
    // Getter e setter
    // -------------------------------
    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
