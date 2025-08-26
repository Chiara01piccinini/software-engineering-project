package org.example.app.model;

import java.math.BigDecimal;
import java.util.UUID;

//rappresenta tutti i prodotti che possono essre caricati
public class Prodotto implements IElemento{
    private final UUID id;
    private String nome;
    private Azienda azienda;
    private Boolean vendita = false;
    private FileInformazioniTestuale descrizione;
    private FileInformazioniImmagini foto;
    private BigDecimal prezzo;
    private int quantita;

    public Prodotto(String nome, Azienda azienda) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.azienda = azienda;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Azienda getAzienda(){return azienda;}

    public void setAzienda (Azienda azienda) {this.azienda = azienda;}

    public Boolean getVendita() {
        return vendita;
    }

    public void setVendita(Boolean vendita) {
        this.vendita = vendita;
    }

    public FileInformazioniTestuale getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(FileInformazioniTestuale descrizione) {
        this.descrizione = descrizione;
    }

    public FileInformazioniImmagini getFoto() {
        return foto;
    }

    public void setFoto(FileInformazioniImmagini foto) {
        this.foto = foto;
    }

    public BigDecimal calcolaPrezzo(){
        return prezzo;
    }

    public void setPrezzo(BigDecimal newPrezzo){
        this.prezzo = newPrezzo;
    }

    public int getQuantità(){
        return quantita;
    }

    public void setQuantità(int quantita){
        this.quantita = quantita;
    }

    //modifica le informazioni del prodotto
    public void aggiungiInformazioni(Messaggio file) {
        if (file instanceof FileInformazioniTestuale testuale)
            this.descrizione = testuale;
        else if (file instanceof FileInformazioniImmagini immagini)
            this.foto = immagini;
    }

    public String getContenuto() {
        return "Prodotto{" + "nome='" + nome + '\'' + ", azienda=" + azienda + '}';
    }
}
