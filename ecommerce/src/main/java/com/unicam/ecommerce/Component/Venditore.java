package com.unicam.ecommerce.Component;

import com.unicam.ecommerce.JsonPersistence.PersistenceManager;
import com.unicam.ecommerce.Service.Gestore;
import com.unicam.ecommerce.Service.Mediator;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Classe astratta che rappresenta un Venditore nel marketplace.
 * Estende Componente, quindi eredita proprietà come nome, cognome e prenotazioni.
 * Ogni venditore è associato a una Azienda.
 */
public class Venditore extends Componente {
    protected Azienda azienda; // Azienda a cui il venditore appartiene

    /**
     * Costruttore per creare un venditore associato a un'azienda.
     * @param nome Nome del venditore
     * @param cognome Cognome del venditore
     * @param azienda Azienda di appartenenza
     */
    public Venditore(TipoAccount tipo,String nome, String cognome, Azienda azienda) {
        super(nome, cognome,tipo);
        this.azienda = azienda;
    }

    /**
     * Richiede l'aggiunta di un nuovo prodotto all'inventario tramite il Gestore.
     * Crea una RichiestaProdotto associata al venditore e all'azienda.
     * @param nomeProdotto Nome del prodotto da aggiungere
     * @param quantita Quantità desiderata
     * @param gestore Gestore incaricato di gestire la richiesta
     */
    public void richiediAggiuntaProdotto(String nomeProdotto, int quantita, Mediator gestore) {
        RichiestaProdotto richiesta = new RichiestaProdotto(nomeProdotto, this.azienda, quantita, this);
        gestore.gestisciRichiestaProdotto(richiesta, this);
    }

    /**
     * Attiva la vendita di un prodotto, impostando il prezzo e abilitando la vendita.
     * @param p Prodotto da vendere
     * @param prezzo Prezzo di vendita
     * @return true se l'operazione è avvenuta con successo, false altrimenti
     */
    public boolean attivaLaVendita(Prodotto p, BigDecimal prezzo) throws IOException {
        if (p != null && prezzo.compareTo(BigDecimal.ZERO) > 0) {
            p.setPrezzo(prezzo);
            p.setVendita(true);

            return true;
        }
        return false;
    }

    /**
     * Richiede la sponsorizzazione di contenuti o prodotti.
     * Invia la richiesta al Gestore solo se il mittente della richiesta è il venditore stesso.
     * @param gestore Gestore che gestisce la pubblicazione
     * @param messaggio Richiesta di sponsorizzazione
     * @param tipo Piattaforma di destinazione
     */
    public void richiediSponsorizzazione(Gestore gestore,
                                         RichiestaSponsorizzazione messaggio,
                                         Piattaforme tipo) throws IOException {
        if (messaggio.getMittente() == this) {
            gestore.pubblicaContenuto(this, messaggio, tipo);
        }
    }

    // Getter e setter per l'azienda
    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }
}
