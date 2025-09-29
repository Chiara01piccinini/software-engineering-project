package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.UUID;

/**
 * Classe che rappresenta un pacchetto di prodotti.
 *
 * Un pacchetto è composto da una mappa di prodotti e può avere uno sconto percentuale.
 * Implementa l'interfaccia IElemento per poter essere trattato come elemento acquistabile.
 *
 * Viene usata anche per il pattern Composite: un pacchetto può contenere più prodotti come un "contenitore".
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pacchetto implements IElemento {

    private Map<Integer, Prodotto> prodotti;       // Prodotti inclusi nel pacchetto (quantità -> prodotto)
    private String nome;                            // Nome del pacchetto
    private final UUID id;                          // Identificatore univoco del pacchetto
    private final BigDecimal percentualeSconto;    // Sconto applicabile al pacchetto
    private InformazioniTestuali descrizione;      // Informazioni aggiuntive sul pacchetto
    private int quantita;                           // Quantità disponibile o desiderata

    /**
     * Costruttore JSON utilizzato per deserializzazione.
     * Verifica che tutti i prodotti siano in vendita, altrimenti solleva eccezione.
     */
    @JsonCreator
    public Pacchetto(
            @JsonProperty("prodotti") Map<Integer,Prodotto> prodotti,
            @JsonProperty("nome") String nome,
            @JsonProperty("sconto") BigDecimal percentualeSconto,
            @JsonProperty("id") UUID id,
            @JsonProperty("quantità") int quantita) {

        for (Map.Entry<Integer, Prodotto> entry : prodotti.entrySet()) {
            int quantitaRichiesta = entry.getKey();
            Prodotto p = entry.getValue();

            // Verifico che il prodotto sia in vendita e che ci sia abbastanza quantità disponibile
            if (!p.getVendita()) {
                throw new RuntimeException("Prodotto non in vendita incluso nel pacchetto");
            }
            if (quantitaRichiesta > p.getQuantita()){
                throw new RuntimeException("quantità non disponibile");

            }

            // Se tutto ok, decremento la quantità disponibile del prodotto
            p.setQuantita(p.getQuantita() - quantitaRichiesta);
        }


        this.nome = nome;
        this.id = UUID.randomUUID();  // L'id viene generato dal sistema
        this.percentualeSconto = percentualeSconto;
        this.prodotti = prodotti;
        this.quantita = quantita;
    }

    /**
     * Costruttore di convenienza senza ID: viene generato automaticamente.
     */
    public Pacchetto(Map<Integer,Prodotto> prodotti, String nome, BigDecimal percentualeSconto, int quantita) {
        this(prodotti, nome, percentualeSconto, UUID.randomUUID(), quantita);
    }

    // -------------------------------
    // Override dei metodi IElemento
    // -------------------------------
    @Override
    public String toString() {
        return nome;
    }

    @Override
    public BigDecimal calcolaPrezzo() {
        BigDecimal totale = BigDecimal.ZERO;

        // Sommo i prezzi di tutti i prodotti nel pacchetto
        for (Prodotto p : prodotti.values()) {
            totale = totale.add(p.calcolaPrezzo());
        }

        // Se non c’è sconto applicabile ritorno il totale pieno
        if (percentualeSconto == null || percentualeSconto.compareTo(BigDecimal.ZERO) <= 0) {
            return totale;
        }

        // Calcolo sconto percentuale e lo applico
        BigDecimal sconto = percentualeSconto.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        return totale.multiply(BigDecimal.ONE.subtract(sconto));
    }

    // -------------------------------
    // Getter e setter
    // -------------------------------
    public Map<Integer, Prodotto> getProdotti() { return prodotti; }
    public void setProdotti(Map<Integer, Prodotto> prodotti) { this.prodotti = prodotti; }

    public void visualizzaProdotti() {
        if(prodotti != null) {
            for(int i = 1; i <= prodotti.size(); i++) {
                Prodotto prodotto = prodotti.get(i);
                System.out.println("Nome prodotto: " + prodotto.getNome() + ", quantità: " + prodotto.getQuantita());
            }
        }
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public UUID getId() { return id; }

    public BigDecimal getPercentualeSconto() { return percentualeSconto; }

    @JsonProperty("descrizione")
    public InformazioniTestuali getDescrizione() { return descrizione; }
    public void setDescrizione(InformazioniTestuali descrizione) { this.descrizione = descrizione; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
}
