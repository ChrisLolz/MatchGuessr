package com.chris.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.backend.models.Team;
import com.chris.backend.repositories.TeamRepository;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Optional<Team> findById(Integer id) {
        if (id == null) return Optional.empty();
        return teamRepository.findById(id);
    }

    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }

    public Set<Team> findByCompetitionId(Integer id) {
        return teamRepository.findByCompetitionsId(id);
    }

    public Team save(Team team) {
        if (team == null) return null;
        return teamRepository.save(team);
    }

    public void deleteById(Integer id) {
        if (id == null) return;
        teamRepository.deleteById(id);
    }
}
