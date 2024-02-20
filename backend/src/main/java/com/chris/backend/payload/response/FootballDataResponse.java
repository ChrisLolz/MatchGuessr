package com.chris.backend.payload.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FootballDataResponse {
    private Filters filters;
    private ResultSet resultSet;
    private Competition competition;
    private MatchResponse matches[];

    public static class Filters {
        private String season;

        public String getSeason() { return season; }

        public void setSeason(String season) { this.season = season; }
    }

    public static class ResultSet {
        public int count;
        public LocalDate startDate;
        public LocalDate endDate;
        public int played;

        public int getCount() { return count; }

        public LocalDate getStartDate() { return startDate; }

        public LocalDate getEndDate() { return endDate; }

        public int getPlayed() { return played; }

        public void setCount(int count) { this.count = count; }

        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

        public void setPlayed(int played) { this.played = played; }
    }

    public static class Competition {
        private int id;
        private String name;
        private String code;
        private String type;
        private String emblem;

        public int getId() { return id; }

        public String getName() { return name; }

        public String getCode() { return code; }

        public String getType() { return type; }

        public String getEmblem() { return emblem; }

        public void setId(int id) { this.id = id; }

        public void setName(String name) { this.name = name; }

        public void setCode(String code) { this.code = code; }

        public void setType(String type) { this.type = type; }

        public void setEmblem(String emblem) { this.emblem = emblem; }
    }

    public static class MatchResponse {
        private Area area;
        private Competition competition;
        private Season season;
        private int id;
        private LocalDateTime utcDate;
        private String status;
        private int matchday;
        private String stage;
        private String group;
        private Team homeTeam;
        private Team awayTeam;
        private Score score;
        private Referee referees[];

        public static class Area {
            private int id;
            private String name;
            private String code;
            private String flag;

            public int getId() { return id; }

            public String getName() { return name; }

            public String getCode() { return code; }

            public String getFlag() { return flag; }

            public void setId(int id) { this.id = id; }

            public void setName(String name) { this.name = name; }

            public void setCode(String code) { this.code = code; }

            public void setFlag(String flag) { this.flag = flag; }
        }

        public static class Season {
            private int id;
            private LocalDate startDate;
            private LocalDate endDate;
            private int currentMatchday;
            private String winner;

            public int getId() { return id; }

            public LocalDate getStartDate() { return startDate; }

            public LocalDate getEndDate() { return endDate; }

            public int getCurrentMatchday() { return currentMatchday; }

            public String getWinner() { return winner; }

            public void setId(int id) { this.id = id; }

            public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

            public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

            public void setCurrentMatchday(int currentMatchday) { this.currentMatchday = currentMatchday; }

            public void setWinner(String winner) { this.winner = winner; }
        }

        public static class Team {
            private int id;
            private String name;
            private String shortName;
            private String tla;
            private String crest;

            public int getId() { return id; }

            public String getName() { return name; }

            public String getShortName() { return shortName; }

            public String getTla() { return tla; }

            public String getCrest() { return crest; }

            public void setId(int id) { this.id = id; }

            public void setName(String name) { this.name = name; }

            public void setShortName(String shortName) { this.shortName = shortName; }

            public void setTla(String tla) { this.tla = tla; }

            public void setCrest(String crest) { this.crest = crest; }
        }

        public static class Score {
            private String winner;
            private String duration;
            private scoreTime fullTime;
            private scoreTime halfTime;

            public static class scoreTime {
                private int home;
                private int away;

                public int getHome() { return home; }

                public int getAway() { return away; }

                public void setHome(int home) { this.home = home; }

                public void setAway(int away) { this.away = away; }
            }

            public String getWinner() { return winner; }

            public String getDuration() { return duration; }

            public scoreTime getFullTime() { return fullTime; }

            public scoreTime getHalfTime() { return halfTime; }

            public void setWinner(String winner) { this.winner = winner; }

            public void setDuration(String duration) { this.duration = duration; }

            public void setFullTime(scoreTime fullTime) { this.fullTime = fullTime; }

            public void setHalfTime(scoreTime halfTime) { this.halfTime = halfTime; }
        }

        public static class Referee {
            private int id;
            private String name;
            private String type;
            private String nationality;

            public int getId() { return id; }

            public String getName() { return name; }

            public String getType() { return type; }

            public String getNationality() { return nationality; }

            public void setId(int id) { this.id = id; }

            public void setName(String name) { this.name = name; }

            public void setType(String type) { this.type = type; }

            public void setNationality(String nationality) { this.nationality = nationality; }
        }

        public Area getArea() { return area; }

        public Competition getCompetition() { return competition; }

        public Season getSeason() { return season; }

        public int getId() { return id; }

        public LocalDateTime getUtcDate() { return utcDate; }

        public String getStatus() { return status; }

        public int getMatchday() { return matchday; }

        public String getStage() { return stage; }

        public String getGroup() { return group; }

        public Team getHomeTeam() { return homeTeam; }

        public Team getAwayTeam() { return awayTeam; }

        public Score getScore() { return score; }

        public Referee[] getReferees() { return referees; }

        public void setArea(Area area) { this.area = area; }

        public void setCompetition(Competition competition) { this.competition = competition; }

        public void setSeason(Season season) { this.season = season; }

        public void setId(int id) { this.id = id; }

        public void setUtcDate(LocalDateTime utcDate) { this.utcDate = utcDate; }

        public void setStatus(String status) { this.status = status; }

        public void setMatchday(int matchday) { this.matchday = matchday; }

        public void setStage(String stage) { this.stage = stage; }

        public void setGroup(String group) { this.group = group; }

        public void setHomeTeam(Team homeTeam) { this.homeTeam = homeTeam; }

        public void setAwayTeam(Team awayTeam) { this.awayTeam = awayTeam; }

        public void setScore(Score score) { this.score = score; }

        public void setReferees(Referee[] referees) { this.referees = referees; }
    }

    public Filters getFilters() { return filters; }

    public ResultSet getResultSet() { return resultSet; }

    public Competition getCompetition() { return competition; }

    public MatchResponse[] getMatches() { return matches; }
}
