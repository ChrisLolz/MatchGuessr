package com.chris.backend.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chris.backend.repositories.AccountRepository;

@Service
public class AccDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public AccDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        return AccDetailsImpl.build(accountRepository.findByUsername(username));
    }
}
