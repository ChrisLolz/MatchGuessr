package com.chris.backend.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.chris.backend.dto.Leaderboard;
import com.chris.backend.dto.MatchAndGuess;
import com.chris.backend.models.Guess;

public interface GuessRepository extends JpaRepository<Guess, Integer> {
    Set<Guess> findByMatchId(Integer matchId);

    Set<Guess> findByAccountId(Long accountId);

    Guess findByAccountIdAndMatchId(Long accountId, Integer matchId);

    @Query(value = "SELECT g.* FROM guess g JOIN match m ON g.match_id = m.id " +
                "JOIN competition c ON m.competition_id = c.id " + 
                "WHERE g.account_id = :accountId "+
                "AND c.season = (SELECT MAX(season) FROM competition WHERE code = :code)", nativeQuery = true)
    Set<Guess> findGuessesByCompetition(String code, Long accountId);

    @Query("SELECT new com.chris.backend.dto.MatchAndGuess(m.id, m.homeTeam, m.awayTeam, m.matchDate, m.competition, m.round, m.homeGoals, m.awayGoals, m.status, g.result) " +
       "FROM Match m " +
       "LEFT JOIN Guess g ON m.id = g.match.id AND g.account.id = :accountId " +
       "WHERE m.competition.id = (SELECT c.id FROM Competition c WHERE c.code = :code AND c.season = (SELECT MAX(c2.season) FROM Competition c2 WHERE c2.code = :code))")
    Set<MatchAndGuess> findMatchAndGuesses(String code, Long accountId);

    @Modifying
    @Query(value = "INSERT INTO guess (account_id, match_id, result) " +
                "SELECT :accountId, :matchId, :result " +
                "WHERE (SELECT match_date FROM match WHERE id = :matchId) > CURRENT_TIMESTAMP " +
                "ON CONFLICT (account_id, match_id) DO UPDATE SET result = :result", nativeQuery = true)
    int save(Long accountId, Integer matchId, Guess.Result result);

    @Query(value="SELECT a.username, SUM(CASE WHEN m.status=2 AND g.result = "+
                "(CASE WHEN m.home_goals > m.away_goals THEN 0 WHEN m.home_goals < m.away_goals THEN 1 ELSE 2 END) THEN 5 ELSE 0 END) as points, "+
            "COUNT(g.id) as guesses, "+
            "COUNT(CASE WHEN m.status=2 AND g.result = "+
                "(CASE WHEN m.home_goals > m.away_goals THEN 0 WHEN m.home_goals < m.away_goals THEN 1 ELSE 2 END) THEN g.id END) as correct_guesses "+
            "FROM account a "+
            "LEFT JOIN Guess g ON a.id = g.account_id "+
            "LEFT JOIN match m ON m.id = g.match_id "+
            "LEFT JOIN competition c ON c.id = m.competition_id AND c.season = (SELECT MAX(season) FROM competition) "+
            "GROUP BY a.username "+
            "ORDER BY points DESC, guesses DESC", nativeQuery = true)
    Set<Leaderboard> getLeaderboard();

    @Query(value="SELECT a.username, SUM(CASE WHEN m.status=2 AND c.code = :code AND g.result = "+
                "(CASE WHEN m.home_goals > m.away_goals THEN 0 WHEN m.home_goals < m.away_goals THEN 1 ELSE 2 END) THEN 5 ELSE 0 END) as points, "+
            "COUNT(CASE WHEN c.code = :code THEN g.id END) as guesses, "+
            "COUNT(CASE WHEN c.code = :code AND m.status=2 AND g.result = "+
                "(CASE WHEN m.home_goals > m.away_goals THEN 0 WHEN m.home_goals < m.away_goals THEN 1 ELSE 2 END) THEN g.id END) as correct_guesses "+
            "FROM account a "+
            "LEFT JOIN Guess g ON a.id = g.account_id "+
            "LEFT JOIN match m ON m.id = g.match_id "+
            "LEFT JOIN competition c ON c.id = m.competition_id AND c.season = (SELECT MAX(season) FROM competition) "+
            "GROUP BY a.username "+
            "ORDER BY points DESC, guesses DESC", nativeQuery = true)
    Set<Leaderboard> getLeaderboardByCompetition(String code);
}
