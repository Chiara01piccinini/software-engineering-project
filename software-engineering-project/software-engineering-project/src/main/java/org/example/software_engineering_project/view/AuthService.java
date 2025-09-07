package org.example.software_engineering_project.view;

import org.example.software_engineering_project.controls.AuthHandler;
import org.example.software_engineering_project.controls.Session;
import org.example.software_engineering_project.model.Account;
import org.example.software_engineering_project.model.Marketplace;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final AuthHandler firstHandler;
    private final Session session;
    private final Marketplace marketplace;

    public AuthService(List<AuthHandler> handlers, Session session, Marketplace marketplace) {
        if (handlers.isEmpty()) throw new IllegalArgumentException("Deve esserci almeno un AuthHandler");
        this.session = session;
        this.marketplace = marketplace;

        // Costruisco la catena di responsabilità
        firstHandler = handlers.get(0);
        AuthHandler current = firstHandler;
        for (int i = 1; i < handlers.size(); i++) {
            current.setNext(handlers.get(i));
            current = handlers.get(i);
        }
    }

    public boolean logIn(String username, String password) {
        System.out.println("[AuthService] Tentativo di login per: " + username);
        boolean success = firstHandler.check(username, password);

        if (success) {
            // Recupera l'account dal Marketplace
            Account user = marketplace.getAccount().values().stream()
                    .filter(a -> a.getNomeUtente().equals(username))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Account non trovato dopo login"));

            // Imposta l'utente nella sessione
            session.setCurrentUser(user);
            session.setAuthenticated(true);
            System.out.println("[AuthService] Login riuscito, utente in sessione: " + user.getNomeUtente());
        } else {
            System.out.println("[AuthService] Login fallito per utente: " + username);
        }
        return success;
    }
}
