package com.chris.backend.payload.response;

import java.time.LocalDate;

import com.chris.backend.payload.response.FootballDataResponse.Competition;
import com.chris.backend.payload.response.FootballDataResponse.Filters;
import com.chris.backend.payload.response.FootballDataResponse.MatchResponse.Area;
import com.chris.backend.payload.response.FootballDataResponse.MatchResponse.Season;

public class FootballDataTeamsResponse {
    private int count;
    private Filters filters;
    private Competition competition;
    private Season season;
    private TeamResponse teams[];

    public static class TeamResponse {
        private Area area;
        private Integer id;
        private String name;
        private String shortName;
        private String tla;
        private String crest;
        private String address;
        private int founded;
        private String clubColors;
        private String venue;
        private Competition runningCompetitions[];
        private Coach Coach;
        private Player squad[];

        public static class Coach {
            private int id;
            private String name;
            private LocalDate dateOfBirth;
            private String nationality;
            private Contract contract;

            public static class Contract {
                private LocalDate start;
                private LocalDate until;

                public LocalDate getStart() { return start; }

                public LocalDate getUntil() { return until; }

                public void setStart(String start) { this.start = LocalDate.parse(start+"-01"); }

                public void setUntil(String until) { this.until = LocalDate.parse(until+"-01"); }
            }

            public int getId() { return id; }

            public String getName() { return name; }

            public LocalDate getDateOfBirth() { return dateOfBirth; }

            public String getNationality() { return nationality; }

            public Contract getContract() { return contract; }

            public void setId(int id) { this.id = id; }

            public void setName(String name) { this.name = name; }

            public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

            public void setNationality(String nationality) { this.nationality = nationality; }

            public void setContract(Contract contract) { this.contract = contract; }
        }

        public static class Player {
            private int id;
            private String name;
            private String position;
            private LocalDate dateOfBirth;
            private String nationality;

            public int getId() { return id; }

            public String getName() { return name; }

            public String getPosition() { return position; }

            public LocalDate getDateOfBirth() { return dateOfBirth; }

            public String getNationality() { return nationality; }

            public void setId(int id) { this.id = id; }

            public void setName(String name) { this.name = name; }

            public void setPosition(String position) { this.position = position; }

            public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

            public void setNationality(String nationality) { this.nationality = nationality; }
        }

        public Area getArea() { return area; }

        public Integer getId() { return id; }

        public String getName() { return name; }

        public String getShortName() { return shortName; }

        public String getTla() { return tla; }

        public String getCrest() { return crest; }

        public String getAddress() { return address; }

        public int getFounded() { return founded; }

        public String getClubColors() { return clubColors; }

        public String getVenue() { return venue; }

        public Competition[] getRunningCompetitions() { return runningCompetitions; }

        public Coach getCoach() { return Coach; }

        public Player[] getSquad() { return squad; }

        public void setArea(Area area) { this.area = area; }

        public void setId(Integer id) { this.id = id; }

        public void setName(String name) { this.name = name; }

        public void setShortName(String shortName) { this.shortName = shortName; }

        public void setTla(String tla) { this.tla = tla; }

        public void setCrest(String crest) { this.crest = crest; }

        public void setAddress(String address) { this.address = address; }

        public void setFounded(int founded) { this.founded = founded; }

        public void setClubColors(String clubColors) { this.clubColors = clubColors; }

        public void setVenue(String venue) { this.venue = venue; }

        public void setRunningCompetitions(Competition[] runningCompetitions) { this.runningCompetitions = runningCompetitions; }

        public void setCoach(Coach Coach) { this.Coach = Coach; }

        public void setSquad(Player[] squad) { this.squad = squad; }
    }

    public int getCount() { return count; }

    public Filters getFilters() { return filters; }

    public Competition getCompetition() { return competition; }

    public Season getSeason() { return season; }

    public TeamResponse[] getTeams() { return teams; }

    public void setCount(int count) { this.count = count; }

    public void setFilters(Filters filters) { this.filters = filters; }

    public void setCompetition(Competition competition) { this.competition = competition; }

    public void setSeason(Season season) { this.season = season; }

    public void setTeams(TeamResponse[] teams) { this.teams = teams; }
}
