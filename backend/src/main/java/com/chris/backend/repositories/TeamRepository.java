package com.chris.backend.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chris.backend.models.Team;

public interface TeamRepository extends JpaRepository<Team, Integer>{
    Team findByName(String name);

    Team findByCode(String code);
    
    Set<Team> findByCompetitionsId(Integer id);

    boolean existsByName(String name);
}
