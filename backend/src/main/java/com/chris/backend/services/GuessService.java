package com.chris.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.backend.dto.Leaderboard;
import com.chris.backend.dto.MatchAndGuess;
import com.chris.backend.models.Guess;
import com.chris.backend.models.Guess.Result;
import com.chris.backend.repositories.GuessRepository;

import jakarta.transaction.Transactional;

@Service
public class GuessService {
    @Autowired
    private GuessRepository guessRepository;

    public List<Guess> findAll() {
        return guessRepository.findAll();
    }

    public Set<Guess> findGuessesByCompetition(String code, Long userId) {
        return guessRepository.findGuessesByCompetition(code, userId);
    }

    public Set<MatchAndGuess> findMatchAndGuesses(String code, Long userId) {
        return guessRepository.findMatchAndGuesses(code, userId);
    }

    public Set<Leaderboard> getLeaderboard() {
        return guessRepository.getLeaderboard();
    }

    public Set<Leaderboard> getLeaderboardByCompetition(String code) {
        return guessRepository.getLeaderboardByCompetition(code);
    }

    public Optional<Guess> findById(Integer userId) {
        if (userId == null) return Optional.empty();
        return guessRepository.findById(userId);
    }


    public Set<Guess> findByMatchId(Integer matchId) {
        return guessRepository.findByMatchId(matchId);
    }

    public Set<Guess> findByAccountId(Long accountId) {
        return guessRepository.findByAccountId(accountId);
    }

    @Transactional
    public boolean save(Long accountId, Integer matchId, Result result) {
        return guessRepository.save(accountId, matchId, result) > 0;
    }

    @Transactional
    public boolean update(Long accountId, Integer matchId, Result result) {
        return guessRepository.save(accountId, matchId, result) > 0;
    }

    public void deleteByID(Integer id) {
        if (id == null) return;
        guessRepository.deleteById(id);
    }
}
