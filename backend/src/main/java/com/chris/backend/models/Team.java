package com.chris.backend.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @NotNull
    @Column(unique=true)
    private String name;

    @NotNull
    @Column(unique=true)
    private String crest;

    @NotNull
    private String code;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "team_competitions",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "competition_id")
    )
    private Set<Competition> competitions = new HashSet<>();

    public Team() {}

    public Team(String name, String code, String crest) {
        this.name = name;
        this.code = code;
        this.crest = crest;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }

    public String getCode() { return code; }

    public String getCrest() { return crest; }

    public Set<Competition> getCompetitions() { return competitions; }

    public void setName(String name) { this.name = name; }

    public void setCode(String code) { this.code = code; }

    public void setCompetitions(Set<Competition> competitions) { this.competitions = competitions; }

    public void addCompetition(Competition competition) { this.competitions.add(competition); }

    public void setCrest(String crest) { this.crest = crest; }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Team)) return false;
        Team t = (Team) o;
        return t.name.equals(this.name);
    }
}
