package org.example.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonCreator
    public Prodotto(@JsonProperty("nome") String nome,@JsonProperty("azienza") Azienda azienda,@JsonProperty("id") UUID id) {
        this.id = id;
        this.nome = nome;
        this.azienda = azienda;
    }

    public Prodotto (String nome, Azienda azienda){
        this(nome,azienda,UUID.randomUUID());
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

    public FileInformazioniImmagini getFoto() {
        return foto;
    }

    public BigDecimal calcolaPrezzo(){
        return prezzo;
    }

    public void setPrezzo(BigDecimal newPrezzo){
        this.prezzo = newPrezzo;
    }

    public int getQuantita(){
        return quantita;
    }

    public void setQuantita(int quantita){
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
