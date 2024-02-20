package com.chris.backend.repositories;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chris.backend.dto.LeagueStandings;
import com.chris.backend.models.Competition;

public interface CompetitionRepository extends JpaRepository<Competition, Integer>{
    Competition findByName(String name);

    Competition findByNameAndSeason(String name, int season);

    Set<Competition> findByCountry(String country);

    Set<Competition> findBySeason(int season);

    @Query(value = "SELECT * FROM Competition c " +
            "WHERE (:date IS NULL OR c.start_date <= :date AND c.end_date >= :date)", nativeQuery = true)
    Set<Competition> findByDate(Date date);
    
    Set<Competition> findByTeamsId(Integer id);

    @Query(value = "SELECT t.name AS team_name, " +
        "c.crest AS competition_crest, " +
        "COUNT(m.id) AS matches_played, "+ 
        "t.crest AS crest," +
        "SUM(CASE WHEN m.home_team_id = t.id AND m.home_goals > m.away_goals THEN 1 "+
                "WHEN m.away_team_id = t.id AND m.away_goals > m.home_goals THEN 1 "+
                "ELSE 0 END) AS wins, "+
        "SUM(CASE WHEN m.home_team_id = t.id AND m.home_goals < m.away_goals THEN 1 "+
                "WHEN m.away_team_id = t.id AND m.away_goals < m.home_goals THEN 1 "+
                "ELSE 0 END) AS losses, "+
        "SUM(CASE WHEN m.home_team_id = t.id AND m.home_goals = m.away_goals THEN 1 "+
                "WHEN m.away_team_id = t.id AND m.away_goals = m.home_goals THEN 1 "+
                "ELSE 0 END) AS draws, "+
        "SUM(CASE WHEN m.home_team_id = t.id THEN m.home_goals "+
                "WHEN m.away_team_id = t.id THEN m.away_goals "+
                "ELSE 0 END) AS goals_for, "+
        "SUM(CASE WHEN m.home_team_id = t.id THEN m.away_goals "+
                "WHEN m.away_team_id = t.id THEN m.home_goals "+
                "ELSE 0 END) AS goals_against, "+
        "(SUM(CASE WHEN m.home_team_id = t.id AND m.home_goals > m.away_goals THEN 3 "+
                "WHEN m.away_team_id = t.id AND m.away_goals > m.home_goals THEN 3 "+
                "WHEN m.home_goals = m.away_goals THEN 1 "+
                "ELSE 0 "+
            "END) - CASE WHEN t.code = 'EVE' THEN 10 ELSE 0 END) AS points, "+
        "SUM(CASE WHEN m.home_team_id = t.id THEN m.home_goals - m.away_goals "+
                "WHEN m.away_team_id = t.id THEN m.away_goals - m.home_goals "+
                "ELSE 0 END) AS goal_difference "+
    "FROM "+
        "competition c "+
        "INNER JOIN team_competitions tc ON c.id = tc.competition_id "+
        "INNER JOIN team t ON tc.team_id = t.id "+
        "LEFT JOIN match m ON (m.home_team_id = t.id OR m.away_team_id = t.id) AND m.status = 2 "+
    "WHERE "+
        "c.code = :code AND " +
        "(:season = 0 AND c.season = (SELECT MAX(season) FROM competition WHERE code = :code) OR c.season = :season) "+
    "GROUP BY "+
        "c.id, t.id "+
    "ORDER BY "+
        "points DESC, goal_difference DESC, goals_for", nativeQuery = true)
    List<LeagueStandings> getLeagueStandings(String code, int season);
}
