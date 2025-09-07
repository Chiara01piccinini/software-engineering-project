package org.example.software_engineering_project.view;


import org.example.software_engineering_project.model.IElemento;
import org.example.software_engineering_project.model.Piattaforme;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SistemaSponsorizzazioni {


    public void pubblica(IElemento s, Piattaforme piattaforma){
        System.out.println("[SistemaSponsorizzazioni] Sponsorizzazione pubblicata per: " + s.getNome()
                + ", piattaforma: " + piattaforma);
    }
}

