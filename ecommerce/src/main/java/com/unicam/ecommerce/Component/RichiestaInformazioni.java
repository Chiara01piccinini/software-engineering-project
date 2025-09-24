package com.unicam.ecommerce.Component;

/**
 * Classe che rappresenta una richiesta di aggiunta di informazioni testuali
 * su un prodotto, pacchetto o evento.
 * Estende la classe astratta Richiesta.
 */
public class RichiestaInformazioni extends Richiesta {

    private String nomeProdotto;       // Nome dell'oggetto (prodotto, pacchetto o evento) a cui aggiungere informazioni
    private InformazioniTestuali info; // Informazioni testuali da aggiungere

    /**
     * Costruttore principale.
     *
     * @param nome         Titolo della richiesta
     * @param mittente     Componente che genera la richiesta
     * @param nomeProdotto Nome dell'oggetto a cui aggiungere le informazioni
     * @param info         Informazioni testuali da aggiungere
     */
    public RichiestaInformazioni(String nome, Componente mittente, String nomeProdotto, InformazioniTestuali info) {
        super(nome, mittente);
        this.nomeProdotto = nomeProdotto;
        this.info = info;
    }

    // -------------------------------
    // Getter e setter
    // -------------------------------
    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public InformazioniTestuali getInfo() {
        return info;
    }

    public void setInfo(InformazioniTestuali info) {
        this.info = info;
    }
}
