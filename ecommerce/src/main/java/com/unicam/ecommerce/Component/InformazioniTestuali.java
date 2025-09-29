package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta informazioni testuali aggiuntive associate a un prodotto,
 * pacchetto o evento.
 *
 * Contiene:
 * - un titolo descrittivo dell'informazione
 * - una descrizione dettagliata
 * - eventuali note aggiuntive
 *
 * Può essere utilizzata, ad esempio, per indicare:
 * - la fase di produzione di un prodotto
 * - istruzioni o dettagli di un pacchetto
 * - informazioni aggiuntive su un evento
 */
public class InformazioniTestuali {

    private String titolo;      // Titolo dell'informazione (es: "Fase di produzione")
    private String descrizione; // Descrizione dettagliata dell'informazione
    private List<String> note;  // Lista di note aggiuntive, opzionale

    /**
     * Costruttore di default.
     * Inizializza la lista delle note vuota.
     */
    public InformazioniTestuali() {
        this.note = new ArrayList<>();
    }

    /**
     * Costruttore completo con titolo e descrizione.
     * @param titolo Titolo dell'informazione
     * @param descrizione Descrizione dettagliata
     */
    @JsonCreator
    public InformazioniTestuali(@JsonProperty("titolo")String titolo,@JsonProperty("descrizione") String descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.note = new ArrayList<>();
    }

    // -------------------------------
    // Getter e Setter
    // -------------------------------
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public List<String> getNote() { return note; }
    public void setNote(List<String> note) { this.note = note; }

    /**
     * Aggiunge una nota alla lista delle note.
     * @param nota Testo della nota da aggiungere
     */
    public void aggiungiNota(String nota) { this.note.add(nota); }

    /**
     * Restituisce una rappresentazione testuale completa dell'oggetto,
     * includendo titolo, descrizione ed eventuali note.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(titolo != null ? titolo + ": " : "");
        sb.append(descrizione != null ? descrizione : "");
        if (!note.isEmpty()) {
            sb.append("\nNote:\n");
            for (String n : note) {
                sb.append("- ").append(n).append("\n");
            }
        }
        return sb.toString();
    }
}
