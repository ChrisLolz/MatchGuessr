package com.chris.backend.controllers;

import com.chris.backend.models.Account;
import com.chris.backend.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService AccountService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> findAll() {
        return AccountService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Account> findByID(@PathVariable Integer id) {
        return AccountService.findByID(id);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public Account findByUsername(@PathVariable String username) {
        return AccountService.findByUsername(username);
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> findByAdmin() {
        return AccountService.findByAdmin(true);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Account save(@RequestBody Account account) {
        return AccountService.save(account);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteByID(@PathVariable Integer id) {
        AccountService.deleteByID(id);
    }
}

