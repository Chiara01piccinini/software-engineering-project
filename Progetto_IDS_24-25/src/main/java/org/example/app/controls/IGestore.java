package org.example.app.controls;

import org.example.app.model.Componente;
import org.example.app.model.Messaggio;

public interface IGestore {
    public void sendInformation(Componente sender, Messaggio event);
    public void sendProduct(Componente sender, Messaggio event);
    public void sendPackage(Componente sender, Messaggio event);
}