package com.chris.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique=true)
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    private String password;

    @NotNull
    private boolean admin=false;

    public Account() {}

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Integer getId() { return id; }
    
    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public boolean isAdmin() { return admin; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setAdmin(boolean admin) { this.admin = admin; }
}
