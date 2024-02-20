package com.chris.backend.dto;

public interface LeagueStandings {
    String getTeam_Name();
    String getCrest();
    String getCompetition_Crest();
    Integer getMatches_Played();
    Integer getWins();
    Integer getDraws();
    Integer getLosses();
    Integer getPoints();
    Integer getGoals_For();
    Integer getGoals_Against();
    Integer getGoal_Difference();
}
