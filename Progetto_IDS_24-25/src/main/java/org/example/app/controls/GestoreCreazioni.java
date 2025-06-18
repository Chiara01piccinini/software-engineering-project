package org.example.app.controls;

import org.example.app.model.*;

public class GestoreCreazioni implements IGestore {
    private DistributoreDiTipicita distributore;
    private Curatore curatore;
    private Trasformatore trasformatore;

    @Override
    public void send(Componente sender, Messaggio event) {
        return;
    }
}
