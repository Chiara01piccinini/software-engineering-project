package com.unicam.ecommerce.Service;

import com.unicam.ecommerce.Component.*;

import java.io.IOException;

public interface Mediator {
    void gestisciRichiestaProdotto(RichiestaProdotto richiesta, Venditore sender);
    void eseguiRichiestaPacchetto(RichiestaPacchetto richiesta) throws IOException;
    void rifiutaRichiesta(Richiesta richiesta);
     void gestisciRichiestaPacchetto(RichiestaPacchetto richiesta, DistributoreDiTipicita sender);
     void gestisciRichiestaEvento(RichiestaEvento richiesta, Animatore sender);
     void gestisciRichiestaInformazioni(RichiestaInformazioni richiesta, Componente sender);
     void eseguiRichiestaProdotto(RichiestaProdotto richiesta) throws IOException;
     void eseguiRichiestaEvento(RichiestaEvento richiesta) throws IOException;
     void eseguiRichiestaInformazioni(RichiestaInformazioni richiesta) throws IOException;
}

