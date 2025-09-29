package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe che rappresenta una richiesta di aggiunta o modifica di un evento.
 * Estende la classe astratta Richiesta.
 * Contiene informazioni specifiche dell'evento, come data, orario, luogo e numero di posti.
 */
public class RichiestaEvento extends Richiesta {

    private LocalDate data;      // Data dell'evento richiesto
    private LocalTime orario;    // Orario dell'evento richiesto
    private Posizione luogo;     // Luogo dell'evento richiesto
    private int posti;           // Numero di posti/biglietti disponibili

    /**
     * Costruttore principale.
     *
     * @param nome       Nome dell'evento
     * @param data       Data dell'evento
     * @param orario     Orario dell'evento
     * @param luogo      Luogo dell'evento
     * @param posti      Numero di posti/biglietti disponibili
     * @param componente Componente che genera la richiesta
     */
    public RichiestaEvento(String nome,LocalDate data, LocalTime orario,Posizione luogo,int posti, Componente componente) {
        super(nome, componente);
        this.data = data;
        this.orario = orario;
        this.luogo = luogo;
        this.posti = posti;
    }

    // -------------------------------
    // Getter e setter
    // -------------------------------

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public void setOrario(LocalTime orario) {
        this.orario = orario;
    }

    public Posizione getLuogo() {
        return luogo;
    }

    public void setLuogo(Posizione luogo) {
        this.luogo = luogo;
    }

    public int getBiglietti() {
        return posti;
    }

    public void setBiglietti(int posti) {
        this.posti = posti;
    }
}
