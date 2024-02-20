package com.chris.backend.models;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Match {
    public enum Status {
        SCHEDULED, LIVE, FINISHED, CANCELLED, POSTPONED
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="match_date")
    private LocalDateTime matchDate;

    @NotNull
    @Column(name="status")
    private Status status = Status.SCHEDULED;

    @NotNull
    @Column(name="round")
    private int round = 0;

    @OneToMany(mappedBy="match")
    private Set<Guess> guesses;

    public Match() {}

    public Match(Team homeTeam, Team awayTeam, Competition competition, int homeGoals, int awayGoals, LocalDateTime matchDate, Status status, int round) {
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

    public void setId(Integer id) { this.id = id; }

    public Team getHomeTeam() { return homeTeam; }

    public Team getAwayTeam() { return awayTeam; }

    public Competition getCompetition() { return competition; }

    public int getHomeGoals() { return homeGoals; }

    public int getAwayGoals() { return awayGoals; }

    public LocalDateTime getMatchDate() { return matchDate; }

    @JsonIgnore
    public Set<Guess> getGuesses() { return guesses; }

    public void setHomeTeam(Team homeTeam) { this.homeTeam = homeTeam; }

    public void setAwayTeam(Team awayTeam) { this.awayTeam = awayTeam; }

    public void setCompetition(Competition competition) { this.competition = competition; }

    public void setHomeGoals(int homeGoals) { this.homeGoals = homeGoals; }

    public void setAwayGoals(int awayGoals) { this.awayGoals = awayGoals; }

    public void setMatchDate(LocalDateTime matchDate) { this.matchDate = matchDate; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public int getRound() { return round; }

    public void setRound(int round) { this.round = round; }

    public void setGuesses(Set<Guess> guesses) { this.guesses = guesses; }

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
