package com.chris.backend.controllers;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;

import com.chris.backend.models.Team;
import com.chris.backend.services.TeamService;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Team> findAll() {
        return teamService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Team> findById(@PathVariable Integer id) {
        return teamService.findById(id);
    }

    @GetMapping("/name/{name}")
    public Team findByName(@PathVariable String name) {
        return teamService.findByName(name);
    }

    @GetMapping("/competition/{id}")
    public Set<Team> findByCompetitionId(@PathVariable Integer id) {
        return teamService.findByCompetitionId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Team save(@RequestBody Team team) {
        return teamService.save(team);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Team update(@RequestBody Team team) {
        return teamService.save(team);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        teamService.deleteById(id);
    }
}
