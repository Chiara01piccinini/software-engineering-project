package org.example.app.model;

import org.example.app.controls.IGestore;

import javax.xml.stream.Location;
import java.util.UUID;

public class ProdottoBase extends Prodotto{
    private Produttore produttore;

    public ProdottoBase( String nome, org.example.app.model.Azienda azienda, Produttore produttore) {
        super(nome, azienda);
        this.produttore = produttore;
    }

}
