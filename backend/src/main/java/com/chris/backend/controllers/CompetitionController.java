package com.chris.backend.controllers;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chris.backend.dto.LeagueStandings;
import com.chris.backend.models.Competition;
import com.chris.backend.services.CompetitionService;

@RestController
@RequestMapping("/competitions")
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;

    @GetMapping
    public List<Competition> findAll() {
        return competitionService.findAll();
    }

    @GetMapping("/name/{name}")
    public Competition findByName(@PathVariable String name) {
        return competitionService.findByName(name);
    }

    @GetMapping("/country/{country}")
    public Set<Competition> findByCountry(@PathVariable String country) {
        return competitionService.findByCountry(country);
    }

    @GetMapping("/team/{id}")
    public Set<Competition> findByTeamId(@PathVariable Integer id) {
        return competitionService.findByTeamId(id);
    }

    @GetMapping("/season/{season}")
    public Set<Competition> findBySeason(@PathVariable int season) {
        return competitionService.findBySeason(season);
    }

    @GetMapping("/date/{date}")
    public Set<Competition> findByDate(@PathVariable Date date) {
        return competitionService.findByDate(date);
    }

    @GetMapping("/standings/{code}/{season}")
    public List<LeagueStandings> getLeagueStandings(@PathVariable String code, @PathVariable int season) {
        return competitionService.getLeagueStandings(code, season);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Competition save(@RequestBody Competition competition) {
        return competitionService.save(competition);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Competition update(@RequestBody Competition competition) {
        return competitionService.save(competition);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        competitionService.deleteByID(id);
    }
}
