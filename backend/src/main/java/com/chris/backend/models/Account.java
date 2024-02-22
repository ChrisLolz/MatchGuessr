package com.chris.backend.models;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique=true)
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    private String password;

    @NotNull
    private List<String> roles;

    @OneToMany(mappedBy="account")
    private Set<Guess> guesses;

    public Account() {}

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = List.of("ROLE_USER");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }

    public String getPassword() { return password; }

    @JsonIgnore
    public Set<Guess> getGuesses() { return guesses; }

    public List<String> getRoles() { return roles; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setRoles(List<String> roles) { this.roles = roles; }

    public void setGuesses(Set<Guess> guesses) { this.guesses = guesses; }
}
