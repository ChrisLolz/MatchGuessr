package com.chris.backend.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
@Entity
public class Competition {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String code;

    @NotNull
    private String country;

    @NotNull
    private int season;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="end_date")
    private LocalDate endDate;

    @ManyToMany(mappedBy="competitions")
    private Set<Team> teams = new HashSet<>();

    public Competition() {}

    public Competition(String name, String code, String country, int season, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.code = code;
        this.country = country;
        this.season = season;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }

    public String getCountry() { return country; }

    public int getSeason() { return season; }

    public LocalDate getStartDate() { return startDate; }

    public LocalDate getEndDate() { return endDate; }

    public Set<Team> getTeams() { return teams; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public void setName(String name) { this.name = name; }

    public void setCountry(String country) { this.country = country; }

    public void setSeason(int season) { this.season = season; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public void setTeams(Set<Team> teams) { this.teams = teams; }

    public void addTeam(Team team) { this.teams.add(team); }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Competition)) return false;
        Competition competition = (Competition) o;
        if (competition.getName().equals(this.name) && 
            competition.getCountry().equals(this.country) && 
            competition.getSeason() == this.season && 
            competition.getStartDate().equals(this.startDate) && 
            competition.getEndDate().equals(this.endDate)) {
            return true;
        } 
        return false;
    }
}
