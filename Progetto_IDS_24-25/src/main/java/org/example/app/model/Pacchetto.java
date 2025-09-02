package org.example.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.UUID;

public class Pacchetto implements IElemento{
    private String nome;
    private final UUID id;
    private BigDecimal prezzo;
    private final BigDecimal percentualeSconto;
    private Set<Prodotto> prodotti;
    private FileInformazioniTestuale descrizione;
    private int quantita;

    @JsonCreator
    public Pacchetto(@JsonProperty("nome") String nome,@JsonProperty("sconto") BigDecimal percentualeSconto,@JsonProperty("prodotti") Set<Prodotto> prodotti,@JsonProperty("id") UUID id, @JsonProperty("quantità") int quantita){
        for (Prodotto p : prodotti){
            if (p.getVendita() == false){
                throw new RuntimeException();
            }
        }

        this.nome = nome;
        this.id = id;
        this.percentualeSconto = percentualeSconto;
        this.prodotti = prodotti;
        this.quantita = quantita;
    }

    public Pacchetto(String nome, BigDecimal percentualeSconto, Set<Prodotto> prodotti, int quantita){
        this(nome,percentualeSconto,prodotti,UUID.randomUUID(),quantita);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal calcolaPrezzo() {
        BigDecimal totale = BigDecimal.ZERO;

        for (Prodotto p : prodotti) {
            totale = totale.add(p.calcolaPrezzo());
        }

        // Se percentualeSconto è null o zero, ritorna il totale
        if (percentualeSconto == null || percentualeSconto.compareTo(BigDecimal.ZERO) <= 0) {
            return totale;
        }

        // Prezzo finale = totale * (1 - sconto/100)
        BigDecimal sconto = percentualeSconto.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        BigDecimal prezzoFinale = totale.multiply(BigDecimal.ONE.subtract(sconto));

        return prezzoFinale;
    }


    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public Set<Prodotto> getProdotti() {
        return prodotti;
    }

    public FileInformazioniTestuale getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(FileInformazioniTestuale descrizione) {
        this.descrizione = descrizione;
    }
    public String getContenuto() {
        String prodottiStr = prodotti.stream()
                .map(Prodotto::getNome)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        String desc = descrizione != null ? descrizione.getContenuto() : "";
        return "Pacchetto: " + nome + ", Prodotti: [" + prodottiStr + "], Prezzo: " + prezzo + ", Descrizione: " + desc;
    }

    public void setProdotti(Set<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getQuantita(){
        return quantita;
    }

    public void aggiungiInformazioni(Messaggio info) {
        if (info instanceof FileInformazioniTestuale) {
            setDescrizione((FileInformazioniTestuale) info);
        } else {
            System.out.println("[Pacchetto] Tipo di informazione non supportato: " + info.getClass().getSimpleName());
        }
    }
}
