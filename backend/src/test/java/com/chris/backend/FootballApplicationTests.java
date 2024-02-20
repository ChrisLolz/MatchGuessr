package com.chris.backend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import com.chris.backend.controllers.CompetitionController;
import com.chris.backend.controllers.GuessController;
import com.chris.backend.controllers.MatchController;
import com.chris.backend.controllers.TeamController;

import com.chris.backend.models.Competition;
import com.chris.backend.models.Match;
import com.chris.backend.models.Team;
import com.chris.backend.services.CompetitionService;
import com.chris.backend.services.GuessService;
import com.chris.backend.services.MatchService;
import com.chris.backend.services.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest({TeamController.class, CompetitionController.class, MatchController.class, GuessController.class})
class FootballApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TeamService teamService;

	@MockBean
	private CompetitionService competitionService;

	@MockBean
	private MatchService matchService;

	@MockBean
	private GuessService guessService;

	private Competition premierLeague = new Competition("Premier League", "PL", "", "England", 2023, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));

	ObjectMapper ObjectMapper = new ObjectMapper();

	Team arsenal = new Team("Arsenal", "ARS", "");
	Team chelsea = new Team("Chelsea", "CHE", "");
	Team liverpool = new Team("Liverpool", "LIV", "");

	@BeforeEach
	void setUp() {
		arsenal.addCompetition(premierLeague);
		chelsea.addCompetition(premierLeague);
		liverpool.addCompetition(premierLeague);
		Mockito.when(teamService.findAll()).thenReturn(List.of(arsenal, chelsea, liverpool));
		ObjectMapper.findAndRegisterModules();
	}

	@Test
	void findAllTeams() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/teams"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Arsenal"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Chelsea"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Liverpool"));
	}

	@Test
	@SuppressWarnings("null")
	void createTeam() throws Exception {
		Team team = new Team("Manchester United", "MUN", "");
		team.addCompetition(premierLeague);
		Mockito.when(teamService.save(Mockito.any(Team.class))).thenReturn(team);
		mockMvc.perform(MockMvcRequestBuilders.post("/teams")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ObjectMapper.writeValueAsString(team)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Manchester United"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.competitions[0].name").value("Premier League"));
		Mockito.when(teamService.findAll()).thenReturn(List.of(arsenal, chelsea, liverpool, team));
		mockMvc.perform(MockMvcRequestBuilders.get("/teams"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[3].name").value("Manchester United"));
	}

	@Test
	@SuppressWarnings("null")
	void createCompetition() throws Exception {
		Competition competition = new Competition("La Liga", "LL", "", "Spain", 2023, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
		ObjectMapper.findAndRegisterModules();
		Mockito.when(competitionService.save(Mockito.any(Competition.class))).thenReturn(competition);
		mockMvc.perform(MockMvcRequestBuilders.post("/competitions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ObjectMapper.writeValueAsString(competition)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("La Liga"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.country").value("Spain"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.season").value(2023))
				.andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(LocalDate.of(2023, 1, 1).toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(LocalDate.of(2023, 12, 31).toString()));
	}

	@Test
	@SuppressWarnings("null")
	void createMatch() throws Exception {
		Match match = new Match(arsenal, chelsea, premierLeague, 2, 1, LocalDateTime.of(2023, 2, 1, 12, 0), Match.Status.FINISHED, 1);
		Mockito.when(matchService.save(Mockito.any(Match.class))).thenReturn(match);
		mockMvc.perform(MockMvcRequestBuilders.post("/matches")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ObjectMapper.writeValueAsString(match)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.homeTeam.name").value("Arsenal"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.awayTeam.name").value("Chelsea"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.homeGoals").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.awayGoals").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("FINISHED"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.round").value(1));
	}

	// @Test
	// @SuppressWarnings("null")
	// void createGuess() throws Exception {
	// 	Match match = new Match(arsenal, chelsea, premierLeague, 2, 1, LocalDateTime.of(2023, 2, 1, 12, 0), Match.Status.FINISHED, 1);
	// 	Mockito.when(matchService.findById(1)).thenReturn(java.util.Optional.of(match));
	// 	mockMvc.perform(MockMvcRequestBuilders.post("/guesses")
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 			.content(ObjectMapper.writeValueAsString(new GuessRequest(1, Result.HOME))))
	// 			.andExpect(MockMvcResultMatchers.status().isCreated());
	// }

	// @Test
	// @SuppressWarnings("null")
	// void createAccount() throws Exception {
	// 	SignupRequest signupRequest = new SignupRequest();
	// 	signupRequest.setUsername("Chrisjghjhgjghj");
	// 	signupRequest.setPassword("1234ghjgjghjghj");
	// 	Mockito.when(accountService.save(Mockito.any(Account.class))).thenReturn(new Account("Chris", "1234"));
	// 	mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 			.content(ObjectMapper.writeValueAsString(signupRequest)))
	// 			.andExpect(MockMvcResultMatchers.status().isOk());
	// }
}
