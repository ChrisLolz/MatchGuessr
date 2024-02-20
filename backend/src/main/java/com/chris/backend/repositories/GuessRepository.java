package com.chris.backend.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.chris.backend.dto.MatchAndGuess;
import com.chris.backend.models.Guess;

public interface GuessRepository extends JpaRepository<Guess, Integer> {
    Set<Guess> findByMatchId(Integer matchId);

    Set<Guess> findByAccountId(Integer accountId);

    Guess findByAccountIdAndMatchId(Integer accountId, Integer matchId);

    @Query(value = "SELECT g.* FROM guess g JOIN match m ON g.match_id = m.id " +
                "JOIN competition c ON m.competition_id = c.id " + 
                "WHERE g.account_id = :userId "+
                "AND c.season = (SELECT MAX(season) FROM competition WHERE code = :code)", nativeQuery = true)
    Set<Guess> findGuessesByCompetition(String code, Integer userId);

    @Query("SELECT new com.chris.backend.dto.MatchAndGuess(m.id, m.homeTeam, m.awayTeam, m.matchDate, m.competition, m.round, m.homeGoals, m.awayGoals, m.status, g.result) " +
       "FROM Match m " +
       "LEFT JOIN Guess g ON m.id = g.match.id AND g.account.id = :userId " +
       "WHERE m.competition.id = (SELECT c.id FROM Competition c WHERE c.code = :code AND c.season = (SELECT MAX(c2.season) FROM Competition c2 WHERE c2.code = :code))")
    Set<MatchAndGuess> findMatchAndGuesses(String code, Integer userId);

    // @Modifying
    // @Query("UPDATE Guess g SET g.result = :result WHERE g.account.id = :accountId AND g.match.id = :matchId AND g.match.match_date > CURRENT_TIMESTAMP")
    // int update(Integer accountId, Integer matchId, Guess.Result result);

    @Modifying
    @Query(value = "INSERT INTO guess (account_id, match_id, result) " +
                "SELECT :accountId, :matchId, :result " +
                "WHERE (SELECT match_date FROM match WHERE id = :matchId) > CURRENT_TIMESTAMP " +
                "ON CONFLICT (account_id, match_id) DO UPDATE SET result = :result", nativeQuery = true)
    int save(Integer accountId, Integer matchId, Guess.Result result);
}
