package com.chris.backend.services;

import com.chris.backend.models.Account;
import com.chris.backend.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findByID(Integer id) {
        if (id == null) return Optional.empty();
        return accountRepository.findById(id);
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public List<Account> findByAdmin(boolean admin) {
        return accountRepository.findByAdmin(admin);
    }

    public Account save(Account account) {
        if (account == null) return null;
        return accountRepository.save(account);
    }

    public Account update(Account account) {
        if (account.getId() == null) return null;
        return accountRepository.save(account);
    }

    public void deleteByID(Integer id) {
        if (id == null) return;
        accountRepository.deleteById(id);
    }
}
