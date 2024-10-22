package com.university.main;

import java.sql.Connection;
import java.util.Scanner;

public abstract class User {
    String email;
    String password;

    public User() {
        this.email=email;
        this.password = password;
    }
    public String getEmail(){
        return email;
    }
    public boolean checkEmail() {
        String regex = "^[a-zA-Z0-9._%+-]+@coed\\.svnit\\.ac\\.in$";
        // Return true if email matches the pattern, false otherwise
        return email.matches(regex);
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    protected abstract void displayMenu(Connection connection, Scanner sc);
}
