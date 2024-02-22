package com.chris.backend.repositories;

import com.chris.backend.models.Account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByUsername(String username);

    boolean existsByUsername(String username);

    List<Account> findByRolesIn(List<String> roles);
}
