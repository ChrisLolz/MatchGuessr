package com.chris.backend.services;

import com.chris.backend.models.Account;
import com.chris.backend.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findByID(Long id) {
        if (id == null) return Optional.empty();
        return accountRepository.findById(id);
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public List<Account> findByRoles(List<String> roles) {
        return accountRepository.findByRolesIn(roles);
    }

    public Account save(Account account) {
        if (account == null) return null;
        return accountRepository.save(account);
    }

    public Account update(Account account) {
        if (account.getId() == null) return null;
        return accountRepository.save(account);
    }

    public void deleteByID(Long id) {
        if (id == null) return;
        accountRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return account;
    }
}
