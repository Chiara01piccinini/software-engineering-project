package org.example.app.model;

import org.example.app.controls.IGestore;

import java.util.List;
import java.util.Map;

public class Marketplace {
    private IGestore gestore;
    private static Map<Integer,Prodotto> prodotti;

    public Marketplace(IGestore gestore) {
        this.gestore = gestore;
    }

    public IGestore getGestore() {
        return gestore;
    }

    public void setGestore(IGestore gestore) {
        this.gestore = gestore;
    }

    public static Map<Integer, Prodotto> getProdotti() {
        return prodotti;
    }

    public static void setProdotti(Map<Integer, Prodotto> prodotti) {
        Marketplace.prodotti = prodotti;
    }

    public static Prodotto getProdottoById(int id){
        return prodotti.get(id);
    }

    public static void aggiungiProdotto(Prodotto p){
        prodotti.put(p.getId(),p);
    }
}
