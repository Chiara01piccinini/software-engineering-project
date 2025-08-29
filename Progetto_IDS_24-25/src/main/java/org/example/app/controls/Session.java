package org.example.app.controls;

import org.example.app.model.Account;

public class Session {

    private static Account currentUser;

    public static void setCurrentUser(Account account) {
        currentUser = account;
    }

    public static Account getCurrentUser() {
        return currentUser;
    }

    public static boolean isAuthenticated() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
    }
}
