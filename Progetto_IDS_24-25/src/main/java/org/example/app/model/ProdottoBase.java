package org.example.app.model;

import org.example.app.controls.IGestore;

import javax.xml.stream.Location;

public class ProdottoBase extends Prodotto{
    private Produttore produttore;

    public ProdottoBase(int id, String nome, org.example.app.model.Azienda azienda, Produttore produttore) {
        super(id, nome, azienda);
        this.produttore = produttore;
    }

    public static class Azienda {
        private String nome;
        private String PI;
        private Location location;
    }

    public abstract static class Componente {
        protected IGestore gestore;
        private String nome, cognome;
        private int matricola;
    }

    public static class Curatore extends Componente {

    }

    public static class DistributoreDiTipicita extends Componente {

    }
}
