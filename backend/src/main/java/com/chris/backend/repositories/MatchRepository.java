package com.chris.backend.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chris.backend.models.Competition;
import com.chris.backend.models.Match;

public interface MatchRepository extends JpaRepository<Match, Integer> {

    Set<Match> findByCompetition(Competition competition);

    @Query(value = "SELECT * FROM Match m " +
            "WHERE (:competitionId IS NULL OR m.competition_id = :competitionId) " +
            "AND (:homeTeamId IS NULL OR m.home_team_id = :homeTeamId) " +
            "AND (:awayTeamId IS NULL OR m.away_team_id = :awayTeamId) " +
            "AND (:matchDate IS NULL OR m.match_date = :matchDate)", nativeQuery = true)
    List<Match> findAll(Integer competitionId, Integer homeTeamId, Integer awayTeamId, LocalDate matchDate);

    @Query(value = "SELECT m.competition_id, away_goals, home_goals, match_date, round, status, m.away_team_id, m.home_team_id, m.id FROM Match m " +
            "JOIN Competition c ON m.competition_id = c.id " +
            "WHERE c.code = :code AND c.start_date <= CURRENT_DATE AND c.end_date >= CURRENT_DATE", nativeQuery = true)
    Set<Match> findByCompetitionCode(String code);
}
