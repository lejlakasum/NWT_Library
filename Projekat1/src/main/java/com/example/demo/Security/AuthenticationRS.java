package com.example.demo.Security;

public class AuthenticationRS {

    private String token;

    public AuthenticationRS() {
    }

    public AuthenticationRS(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
