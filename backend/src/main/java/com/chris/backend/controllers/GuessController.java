package com.chris.backend.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chris.backend.dto.MatchAndGuess;
import com.chris.backend.models.Guess;
import com.chris.backend.payload.request.GuessRequest;
import com.chris.backend.security.services.AccDetailsImpl;
import com.chris.backend.services.GuessService;

@RestController
@RequestMapping("/guess")
public class GuessController {
    @Autowired
    private GuessService guessService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Guess> findAll() {
        return guessService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Optional<Guess> findById(@PathVariable Integer id) {
        if (id == null) return Optional.empty();
        return guessService.findById(id);
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @GetMapping("/account/{id}")
    public Set<Guess> findByAccountId(@PathVariable Integer id) {
        return guessService.findByAccountId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/match/{id}")
    public Set<Guess> findByMatchId(@PathVariable Integer id) {
        return guessService.findByMatchId(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/competition/{code}")
    public Set<Guess> findGuessesByCompetition(@PathVariable String code, Authentication authentication) {
        AccDetailsImpl userDetails = (AccDetailsImpl)(authentication.getPrincipal());
        Integer userId = userDetails.getId();
        return guessService.findGuessesByCompetition(code, userId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/matches/{code}")
    public Set<MatchAndGuess> findMatchAndGuesses(@PathVariable String code) {
        AccDetailsImpl userDetails = (AccDetailsImpl)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Integer userId = userDetails.getId();
        return guessService.findMatchAndGuesses(code, userId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody GuessRequest request) {
        try {
            AccDetailsImpl userDetails = (AccDetailsImpl)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.status(HttpStatus.CREATED).body(guessService.save(userDetails.getId(), request.getMatchId(), request.getResult()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody GuessRequest request) {
        try {
            AccDetailsImpl userDetails = (AccDetailsImpl)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(guessService.update(userDetails.getId(), request.getMatchId(), request.getResult()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public void deleteByID(@PathVariable Integer id) {
        if (id == null) return;
        guessService.deleteByID(id);
    }

}
