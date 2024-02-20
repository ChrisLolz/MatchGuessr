package com.chris.backend.dto;

import java.time.LocalDateTime;

import com.chris.backend.models.Competition;
import com.chris.backend.models.Team;
import com.chris.backend.models.Guess.Result;
import com.chris.backend.models.Match.Status;

public class MatchAndGuess {
    private Integer id;
    private Team homeTeam;
    private Team awayTeam;
    private LocalDateTime matchDate;
    private Competition competition;
    private Integer round;
    private Integer homeGoals;
    private Integer awayGoals;
    private Status status;
    private Result result;

    public MatchAndGuess(Integer id, Team homeTeam, Team awayTeam, LocalDateTime matchDate, Competition competition, Integer round, Integer homeGoals, Integer awayGoals, Status status, Result result) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.competition = competition;
        this.round = round;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.status = status;
        this.result = result;
    }

    public Integer getId() { return id; }

    public Team getHomeTeam() { return homeTeam; }

    public Team getAwayTeam() { return awayTeam; }

    public LocalDateTime getMatchDate() { return matchDate; }

    public Competition getCompetition() { return competition; }

    public Integer getRound() { return round; }

    public Integer getHomeGoals() { return homeGoals; }

    public Integer getAwayGoals() { return awayGoals; }

    public Status getStatus() { return status; }

    public Result getResult() { return result; }

    public void setId(Integer id) { this.id = id; }

    public void setHomeTeam(Team homeTeam) { this.homeTeam = homeTeam; }

    public void setAwayTeam(Team awayTeam) { this.awayTeam = awayTeam; }

    public void setMatchDate(LocalDateTime matchDate) { this.matchDate = matchDate; }

    public void setCompetition(Competition competition) { this.competition = competition; }

    public void setRound(Integer round) { this.round = round; }

    public void setHomeGoals(Integer homeGoals) { this.homeGoals = homeGoals; }

    public void setAwayGoals(Integer awayGoals) { this.awayGoals = awayGoals; }

    public void setStatus(Status status) { this.status = status; }

    public void setResult(Result result) { this.result = result; }
}