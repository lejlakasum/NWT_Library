package com.example.demo.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ValidationRS {

    private String username;

    private String password;

    private String authorities;

    public ValidationRS() {
    }

    public ValidationRS(String username, String password, String authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthorities() {
        return authorities;
    }
}
