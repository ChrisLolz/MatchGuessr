package com.chris.backend.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import com.chris.backend.models.Match;
import com.chris.backend.services.MatchService;

@RestController
@RequestMapping("/api/matches")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @GetMapping
    public List<Match> findAll(@RequestParam(required = false) Integer competitionId,
                               @RequestParam(required = false) Integer homeTeamId,
                               @RequestParam(required = false) Integer awayTeamId,
                               @RequestParam(required = false) LocalDate matchDate) {
        return matchService.findAll(competitionId, homeTeamId, awayTeamId, matchDate);
    }

    @GetMapping("/{id}")
    public Optional<Match> findById(@PathVariable Integer id) {
        return matchService.findById(id);
    }

    @GetMapping("/competition/{code}")
    public Set<Match> findByCompetitionCode(@PathVariable String code) {
        return matchService.findByCompetitionCode(code);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Match save(@RequestBody Match match) {
        return matchService.save(match);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Match update(@RequestBody Match match) {
        return matchService.save(match);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        matchService.deleteByID(id);
    }
}
