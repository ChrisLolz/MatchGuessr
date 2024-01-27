package com.chris.backend.repositories;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chris.backend.models.Competition;
import com.chris.backend.models.Match;

public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query(value = "SELECT * FROM Match m " +
            "WHERE (:competitionId IS NULL OR m.competition_id = :competitionId) " +
            "AND (:homeTeamId IS NULL OR m.home_team_id = :homeTeamId) " +
            "AND (:awayTeamId IS NULL OR m.away_team_id = :awayTeamId) " +
            "AND (:matchDate IS NULL OR m.match_date = :matchDate)", nativeQuery = true)
    Set<Match> findByQueries(
            @Param("competitionId") Integer competitionId,
            @Param("homeTeamId") Integer homeTeamId,
            @Param("awayTeamId") Integer awayTeamId,
            @Param("matchDate") LocalDate matchDate
    );

    Set<Match> findByCompetition(Competition competition);
}
