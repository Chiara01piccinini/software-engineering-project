package org.example.app.view;

import org.example.app.controls.AuthHandler;

public class AuthService {
    private AuthHandler handler;

    public AuthService(AuthHandler handler) {
        this.handler = handler;
    }

    public boolean logIn(String username, String password) {
        System.out.println("[Auth] Tentativo di login per: " + username);
        return handler.check(username, password);
    }
}

