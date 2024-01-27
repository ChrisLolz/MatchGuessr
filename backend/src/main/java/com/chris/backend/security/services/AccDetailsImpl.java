package com.chris.backend.security.services;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.chris.backend.models.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public AccDetailsImpl(Integer id, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static AccDetailsImpl build(Account account) {
        return new AccDetailsImpl(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                account.isAdmin() ? List.of(() -> "ROLE_ADMIN") : List.of(() -> "ROLE_USER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getId() { return id;}
    
    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccDetailsImpl)) return false;
        AccDetailsImpl user = (AccDetailsImpl) o;
        return id.equals(user.id);
    }
}
