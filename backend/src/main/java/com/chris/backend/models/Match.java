package com.chris.backend.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Match {
    public enum Status {
        SCHEDULED, LIVE, FINISHED, CANCELED
    }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="home_team_id")
    private Team homeTeam;

    @NotNull
    @ManyToOne
    @JoinColumn(name="away_team_id")
    private Team awayTeam;

    @NotNull
    @ManyToOne
    @JoinColumn(name="competition_id")
    private Competition competition;

    @NotNull
    @Column(name="home_goals")
    private int homeGoals = 0;

    @NotNull
    @Column(name="away_goals")
    private int awayGoals = 0;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="match_date")
    private LocalDate matchDate;

    @NotNull
    @Column(name="status")
    private Status status = Status.SCHEDULED;

    @NotNull
    @Column(name="round")
    private int round = 0;

    public Match() {}

    public Match(Team homeTeam, Team awayTeam, Competition competition, int homeGoals, int awayGoals, LocalDate matchDate, Status status, int round) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.competition = competition;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.matchDate = matchDate;
        this.status = status;
        this.round = round;
    }

    public Integer getId() { return id; }

    public Team getHomeTeam() { return homeTeam; }

    public Team getAwayTeam() { return awayTeam; }

    public Competition getCompetition() { return competition; }

    public int getHomeGoals() { return homeGoals; }

    public int getAwayGoals() { return awayGoals; }

    public LocalDate getMatchDate() { return matchDate; }

    public void setHomeTeam(Team homeTeam) { this.homeTeam = homeTeam; }

    public void setAwayTeam(Team awayTeam) { this.awayTeam = awayTeam; }

    public void setCompetition(Competition competition) { this.competition = competition; }

    public void setHomeGoals(int homeGoals) { this.homeGoals = homeGoals; }

    public void setAwayGoals(int awayGoals) { this.awayGoals = awayGoals; }

    public void setMatchDate(LocalDate matchDate) { this.matchDate = matchDate; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public int getRound() { return round; }

    public void setRound(int round) { this.round = round; }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Match)) {
            return false;
        }
        Match m = (Match) o;

        return m.getHomeTeam().equals(this.homeTeam) && 
            m.getAwayTeam().equals(this.awayTeam) && 
            m.getCompetition().equals(this.competition) && 
            m.getStatus() == this.status &&
            m.getRound() == this.round;
    }
}
