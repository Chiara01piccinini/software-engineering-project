package org.example.software_engineering_project.controls;

import org.example.software_engineering_project.model.Componente;
import org.example.software_engineering_project.model.Messaggio;

public interface  IGestore {
    public void inviaInformazioni(Componente sender, Messaggio event);
    public void inviaProdotto(Componente sender, Messaggio event);
    public void inviaPacchetto(Componente sender, Messaggio event);
}
