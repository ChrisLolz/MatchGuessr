package com.chris.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "match_id"}))
public class Guess {
    public enum Result {
        HOME, AWAY, DRAW
    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    @NotNull
    @ManyToOne
    @JoinColumn(name="match_id")
    private Match match;

    @NotNull
    private Result result;

    public Guess() {}

    public Guess(Account user, Match match, Result result) {
        this.account = user;
        this.match = match;
        this.result = result;
    }

    public Integer getId() { return id; }

    @JsonIgnore
    public Account getUser() { return account; }

    public Match getMatch() { return match; }

    public Result getResult() { return result; }

    public void setUser(Account account) { this.account = account; }
    
    public void setMatch(Match match) { this.match = match; }

    public void setResult(Result result) { this.result = result; }
}
