package com.chris.backend.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.chris.backend.models.Competition;
import com.chris.backend.models.Match;
import com.chris.backend.models.Team;
import com.chris.backend.payload.response.FootballDataResponse;
import com.chris.backend.payload.response.FootballDataTeamsResponse;
import com.chris.backend.payload.response.FootballDataResponse.MatchResponse;
import com.chris.backend.payload.response.FootballDataTeamsResponse.TeamResponse;
import com.chris.backend.repositories.CompetitionRepository;
import com.chris.backend.repositories.MatchRepository;
import com.chris.backend.repositories.TeamRepository;

import jakarta.transaction.Transactional;

@EnableAsync
@Transactional
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
    
    @Async
    public void setLeague(String code) {
        long startTime = System.currentTimeMillis(); 
        try {
            FootballDataTeamsResponse response = webClient.get()
                .uri("competitions/" + code + "/teams")
                .retrieve()
                .bodyToMono(FootballDataTeamsResponse.class)
                .block();
            Competition league = new Competition();
            league.setName(response.getCompetition().getName());
            league.setCountry(response.getTeams()[0].getArea().getName());
            league.setCode(code);
            //https://upload.wikimedia.org/wikipedia/en/thumb/f/f2/Premier_League_Logo.svg/1200px-Premier_League_Logo.svg.png
            league.setCrest(response.getCompetition().getEmblem());
            league.setSeason(Integer.parseInt(response.getFilters().getSeason()));
            league.setStartDate(response.getSeason().getStartDate());
            league.setEndDate(response.getSeason().getEndDate());
            if (competitionRepository.findByNameAndSeason(league.getName(), league.getSeason()) == null) {
                competitionRepository.save(league);
            } else {
                league = competitionRepository.findByNameAndSeason(league.getName(), league.getSeason());
            }
            Set<Team> teamsToSave = new HashSet<>();
            for (TeamResponse team : response.getTeams()) {
                Team t = teamRepository.findByName(team.getShortName());
                if (t == null) {
                    t = new Team(team.getShortName(), team.getTla(), team.getCrest());
                    t.addCompetition(league);
                    teamsToSave.add(t);
                } else if (!t.getCompetitions().contains(league)) {
                    t.addCompetition(league);
                    teamsToSave.add(t);
                }
            }
            teamRepository.saveAll(teamsToSave);
            int season = league.getSeason();
            FootballDataResponse response2 = webClient.get()
                .uri("competitions/" + code + "/matches?season=" + season)
                .retrieve()
                .bodyToMono(FootballDataResponse.class)
                .block();
            Set<Match> matchesToSave = new HashSet<>();
            Map<String, Team> teams = new HashMap<>();
            Map<String, Match> dbMatches = new HashMap<>();
            for (Match m : matchRepository.findByCompetition(league)) {
                dbMatches.put(m.getHomeTeam().getCode() + m.getRound(), m);
            }
            for (Team t : teamRepository.findByCompetitionsId(league.getId())) {
                teams.put(t.getCode(), t);
            }
            for (MatchResponse matches : response2.getMatches()) {
                Match m = dbMatches.get(matches.getHomeTeam().getTla() + matches.getMatchday());
                if (m == null) m = new Match();
                m.setCompetition(league);
                if (matches.getStatus().equals("FINISHED")) {
                    m.setStatus(Match.Status.FINISHED);
                } else if (matches.getStatus().equals("SCHEDULED")) {
                    m.setStatus(Match.Status.SCHEDULED);
                } else if (matches.getStatus().equals("IN_PLAY")) {
                    m.setStatus(Match.Status.LIVE);
                } else if (matches.getStatus().equals("TIMED")) {
                    m.setStatus(Match.Status.SCHEDULED);
                } else if (matches.getStatus().equals("PAUSED")) {
                    m.setStatus(Match.Status.LIVE);
                } else if (matches.getStatus().equals("POSTPONED")) {
                    m.setStatus(Match.Status.POSTPONED);
                } else if (matches.getStatus().equals("SUSPENDED")) {
                    m.setStatus(Match.Status.SCHEDULED);
                } else if (matches.getStatus().equals("CANCELED")) {
                    m.setStatus(Match.Status.CANCELLED);
                } else if (matches.getStatus().equals("AWARDED")) {
                    m.setStatus(Match.Status.FINISHED);
                }
                m.setHomeTeam(teams.get(matches.getHomeTeam().getTla()));
                m.setAwayTeam(teams.get(matches.getAwayTeam().getTla()));
                m.setRound(matches.getMatchday());
                m.setMatchDate(matches.getUtcDate());
                if (matches.getScore().getWinner() != null) {
                    m.setHomeGoals(matches.getScore().getFullTime().getHome());
                    m.setAwayGoals(matches.getScore().getFullTime().getAway());
                }
                matchesToSave.add(m);
            }
            matchRepository.saveAll(matchesToSave);
            System.out.println(code + " refreshed in " + (System.currentTimeMillis() - startTime)/1000 + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
