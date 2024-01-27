package com.chris.backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.backend.dto.LeagueStandings;
import com.chris.backend.models.Competition;
import com.chris.backend.repositories.CompetitionRepository;

@Service
public class CompetitionService {
    @Autowired
    private CompetitionRepository competitionRepository;

    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    public Optional<Competition> findById(Integer id) {
        if (id == null) return Optional.empty();
        return competitionRepository.findById(id);
    }

    public Competition findByName(String name) {
        return competitionRepository.findByName(name);
    }

    public Set<Competition> findByCountry(String country) {
        return competitionRepository.findByCountry(country);
    }

    public Set<Competition> findByTeamId(Integer id) {
        return competitionRepository.findByTeamsId(id);
    }

    public Set<Competition> findBySeason(int season) {
        return competitionRepository.findBySeason(season);
    }

    public Set<Competition> findByDate(Date date) {
        return competitionRepository.findByDate(date);
    }

    public List<LeagueStandings> getLeagueStandings(String code, int season) {
        return competitionRepository.getLeagueStandings(code, season);
    }

    public Competition save(Competition competition) {
        if (competition == null) return null;
        return competitionRepository.save(competition);
    }

    public void deleteByID(Integer id) {
        if (id == null) return;
        competitionRepository.deleteById(id);
    }
}
