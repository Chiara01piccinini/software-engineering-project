package org.example.app;

public class GestoreCreazioni implements IGestore{
    private DistributoreDiTipicita distributore;
    private Curatore curatore;
    private Trasformatore trasformatore;


    @Override
    public void send(Componente sender, Messaggio event) {

    }
}
