package com.unicam.ecommerce.Service;

import com.unicam.ecommerce.Component.*;

public interface Mediator {
    void gestisciRichiestaProdotto(RichiestaProdotto richiesta, Venditore sender);
    void eseguiRichiestaPacchetto(RichiestaPacchetto richiesta);
    void rifiutaRichiesta(Richiesta richiesta);
     void gestisciRichiestaPacchetto(RichiestaPacchetto richiesta, DistributoreDiTipicita sender);
     void gestisciRichiestaEvento(RichiestaEvento richiesta, Animatore sender);
     void gestisciRichiestaInformazioni(RichiestaInformazioni richiesta, Componente sender);
     void eseguiRichiestaProdotto(RichiestaProdotto richiesta) ;
     void eseguiRichiestaEvento(RichiestaEvento richiesta);
     void eseguiRichiestaInformazioni(RichiestaInformazioni richiesta);
}

