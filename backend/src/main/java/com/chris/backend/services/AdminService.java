package com.chris.backend.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.chris.backend.models.Competition;
import com.chris.backend.models.Match;
import com.chris.backend.models.Team;
import com.chris.backend.repositories.CompetitionRepository;
import com.chris.backend.repositories.MatchRepository;
import com.chris.backend.repositories.TeamRepository;

@Service
public class AdminService {
    @Autowired
    private WebClient webClient;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;
    
    public void setPremierLeague() {

        try {
            Map<String, Object> response = webClient.get()
                .uri("competitions/PL/teams")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
            Competition premierLeague = new Competition();
            premierLeague.setName("Premier League");
            premierLeague.setCountry("England");
            premierLeague.setCode("PL");
            premierLeague.setSeason(Integer.parseInt((String) ((Map<String, Object>) response.get("filters")).get("season")));
            premierLeague.setStartDate(LocalDate.parse((String) ((Map<String, Object>) response.get("season")).get("startDate")));
            premierLeague.setEndDate(LocalDate.parse((String) ((Map<String, Object>) response.get("season")).get("endDate")));
            if (competitionRepository.findByNameAndSeason("Premier League", premierLeague.getSeason()) == null) {
                competitionRepository.save(premierLeague);
            } else {
                premierLeague = competitionRepository.findByNameAndSeason("Premier League", premierLeague.getSeason());
            }
            Set<Team> teamsToSave = new HashSet<>();
            for (Map<String, Object> team : (List<Map<String, Object>>) response.get("teams")) {
                Team t = teamRepository.findByCode((String) team.get("tla"));
                if (t == null) {
                    t = new Team((String) team.get("name"), (String) team.get("tla"));
                    t.addCompetition(premierLeague);
                    teamsToSave.add(t);
                } else if (!t.getCompetitions().contains(premierLeague)) {
                    t.addCompetition(premierLeague);
                    teamsToSave.add(t);
                }
            }
            teamRepository.saveAll(teamsToSave);
            int season = premierLeague.getSeason();
            Map<String, Object> response2 = webClient.get()
                .uri("competitions/PL/matches?season=" + season)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Set<Match> matchesToSave = new HashSet<>();
            Map<String, Team> teams = new HashMap<>();
            Map<String, Match> dbMatches = new HashMap<>();
            for (Match m : matchRepository.findByCompetition(premierLeague)) {
                dbMatches.put(m.getHomeTeam().getCode() + m.getRound(), m);
            }
            for (Team t : teamRepository.findByCompetitionsId(premierLeague.getId())) {
                teams.put(t.getCode(), t);
            }
            for (Map<String, Object> matches : (List<Map<String, Object>>) response2.get("matches")) {
                Match m = dbMatches.get((String) ((Map<String, Object>) matches.get("homeTeam")).get("tla") + (int) matches.get("matchday"));
                if (m == null) m = new Match();
                m.setCompetition(premierLeague);
                if (matches.get("status").equals("FINISHED")) {
                    m.setStatus(Match.Status.FINISHED);
                } else if (matches.get("status").equals("SCHEDULED")) {
                    m.setStatus(Match.Status.SCHEDULED);
                } else if (matches.get("status").equals("IN_PLAY")) {
                    m.setStatus(Match.Status.LIVE);
                } else if (matches.get("status").equals("TIMED")) {
                    m.setStatus(Match.Status.SCHEDULED);
                } else if (matches.get("status").equals("PAUSED")) {
                    m.setStatus(Match.Status.LIVE);
                } else if (matches.get("status").equals("POSTPONED")) {
                    m.setStatus(Match.Status.SCHEDULED);
                } else if (matches.get("status").equals("SUSPENDED")) {
                    m.setStatus(Match.Status.SCHEDULED);
                } else if (matches.get("status").equals("CANCELED")) {
                    m.setStatus(Match.Status.CANCELED);
                }
                m.setHomeTeam(teams.get((String) ((Map<String, Object>) matches.get("homeTeam")).get("tla")));
                m.setAwayTeam(teams.get((String) ((Map<String, Object>) matches.get("awayTeam")).get("tla")));
                m.setRound((int) matches.get("matchday"));
                m.setMatchDate(LocalDate.parse((String) matches.get("utcDate"), formatter));
                if (((Map<String, Object>) matches.get("score")).get("winner") != null) {
                    m.setHomeGoals((int) ((Map<String, Object>) ((Map<String, Object>) matches.get("score")).get("fullTime")).get("home"));
                    m.setAwayGoals((int) ((Map<String, Object>) ((Map<String, Object>) matches.get("score")).get("fullTime")).get("away"));
                }
                matchesToSave.add(m);
            }
            matchRepository.saveAll(matchesToSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
