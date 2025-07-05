package org.example.app.controls;

import org.example.app.model.Componente;
import org.example.app.model.Messaggio;

public interface IGestore {
    public void inviaInformazioni(Componente sender, Messaggio event);
    public void inviaProdotto(Componente sender, Messaggio event);
    public void inviaPacchetto(Componente sender, Messaggio event);

}