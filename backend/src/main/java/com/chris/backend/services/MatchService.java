package com.chris.backend.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.backend.models.Match;
import com.chris.backend.repositories.MatchRepository;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    public Optional<Match> findById(Integer id) {
        if (id == null) return Optional.empty();
        return matchRepository.findById(id);
    }

    public Set<Match> findByQueries(Integer competitionId, Integer homeTeamId, Integer awayTeamId, LocalDate matchDate) {
        return matchRepository.findByQueries(competitionId, homeTeamId, awayTeamId, matchDate);
    }

    public Match save(Match match) {
        if (match == null) return null;
        return matchRepository.save(match);
    }

    public void deleteByID(Integer id) {
        if (id == null) return;
        matchRepository.deleteById(id);
    }
}
