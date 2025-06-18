package org.example.app.controls;

import org.example.app.model.Componente;
import org.example.app.model.Messaggio;

public interface IGestore {
    public void send(Componente sender, Messaggio event);
}