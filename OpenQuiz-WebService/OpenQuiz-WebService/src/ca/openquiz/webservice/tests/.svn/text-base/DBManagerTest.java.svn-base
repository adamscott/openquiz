package ca.openquiz.webservice.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.appengine.api.datastore.Key;

import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Game;
import ca.openquiz.webservice.model.Group;
import ca.openquiz.webservice.model.GroupRole;
import ca.openquiz.webservice.model.Question;
import ca.openquiz.webservice.model.QuestionAnagram;
import ca.openquiz.webservice.model.QuestionGeneral;
import ca.openquiz.webservice.model.Team;
import ca.openquiz.webservice.model.Tournament;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.parameter.SearchGameParam;
import ca.openquiz.webservice.parameter.SearchGroupParam;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;
import ca.openquiz.webservice.parameter.SearchQuestionParam;
import ca.openquiz.webservice.parameter.SearchTeamParam;
import ca.openquiz.webservice.parameter.SearchTournamentParam;
import ca.openquiz.webservice.parameter.SearchUserParam;

public class DBManagerTest extends BaseTest
{
/*********************************************************************************************************************************
 * User test
 ********************************************************************************************************************************/
	@Test
	public void getUserTest()
	{
		initUser();
		
		User newUser = DBManager.getUser(user.getKey());
		
		assertEquals("FirstNameTest", "FirstNameTest", newUser.getFirstName());
	}
	
	@Test
	public void searchUsersTeamTest()
	{
		initTeam();
		initUsers();
		
		team.addUser(user);
		team.addUser(user2);
		
		SearchUserParam param = new SearchUserParam();
		param.setTeam(team.getKey());		
		List<User> userList = DBManager.searchUsers(param);
		
		assertEquals("getUsersForTeamTest", "test@email.com", userList.get(0).getEmail());
		assertEquals("getUsersForTeamTest", "user2@email.com", userList.get(1).getEmail());
	}
	
/*********************************************************************************************************************************
 * Team test
 ********************************************************************************************************************************/
	@Test
	public void getTeamTest()
	{
		initTeam();
		
		Team newTeam = DBManager.getTeam(team.getKey());
		
		assertEquals("getTeamTest", "nameTest", newTeam.getName());
	}
	
//	@Test
//	public void searchTeamsTournamentTest()
//	{
//		initTournament();
//		initTeams();
//		
//		//Test des 2 facons d'ajouter une equipe a un tournoi
//		tournament.addTeam(team);
//		team2.addTournament(tournament);
//		
//		List<Team> teamsList = DBManager.getTeamsForTournament(tournament);
//		
//		assertEquals("getTeamsForTournamentTest", "nameTest", teamsList.get(0).getName());		
//		assertEquals("getTeamsForTournamentTest", "nameTeam2", teamsList.get(1).getName());	
//	}
	

	@Test
	public void searchTeamsGroupTest()
	{
		initTeams();
		initGroup();
		team.setGroup(group);
		
		SearchTeamParam searchParam = new SearchTeamParam();
		searchParam.setGroup(group.getKey());
		searchParam.setActive(true);
		List<Team> teams = DBManager.searchTeams(searchParam);
		
		assertEquals("getSearchTeams group", group.getKey(), teams.get(0).getGroup());	
	}
	
	@Test
	public void searchTeamsUserTest()
	{
		initGroup();
		initTeam();
		initUser();
		new GroupRole(user, group, Role.Player);
		
		team.setGroup(group);
		user.addTeam(team);
		
		SearchTeamParam searchParam = new SearchTeamParam();
		searchParam.setUser(user.getKey());
		List<Team> teams = DBManager.searchTeams(searchParam);
		
		assertEquals("getSearchTeams user", user.getKey(), teams.get(0).getUsers().get(0));	
	}
	
/*********************************************************************************************************************************
 * Tournament test
 ********************************************************************************************************************************/
	
	@Test
	public void getTournamentTest()
	{
		initTournament();
		
		Tournament newTournament = DBManager.getTournament(tournament.getKey());
		
		assertEquals("getTournamentTest", "nameTournament", newTournament.getName());
		assertEquals("getTournamentTest", "123 rue test", newTournament.getAddress());
		assertEquals("getTournamentTest", "Sherbrooke", newTournament.getCity());
		assertEquals("getTournamentTest", "Canada", newTournament.getCountry());
	}
	
	@Test
	public void searchTournamentTeamTest()
	{
		initTournaments();
		initTeam();
		
		//Test des 2 facons d'ajouter un tournoi a une equipe
		team.addTournament(tournament);
		tournament2.addTeam(team);

		SearchTournamentParam searchParam = new SearchTournamentParam();
		searchParam.setTeam(team.getKey());		
		List<Tournament> tournamentsList = DBManager.searchTournament(searchParam);
		
		assertEquals("searchTournamentTeamTest", "nameTournament", tournamentsList.get(0).getName());
		assertEquals("searchTournamentTeamTest", "123 rue test", tournamentsList.get(0).getAddress());
		assertEquals("searchTournamentTeamTest", "Sherbrooke", tournamentsList.get(0).getCity());
		assertEquals("searchTournamentTeamTest", "Canada", tournamentsList.get(0).getCountry());
		
		assertEquals("searchTournamentTeamTest", "nameTournament2", tournamentsList.get(1).getName());
		assertEquals("searchTournamentTeamTest", "456 rue test", tournamentsList.get(1).getAddress());
		assertEquals("searchTournamentTeamTest", "Farnham", tournamentsList.get(1).getCity());
		assertEquals("searchTournamentTeamTest", "Russie", tournamentsList.get(1).getCountry());		
	}
	
	@Test
	public void searchTournamentGameTest()
	{
		initTournaments();
		initGame();
		
		game.addTournament(tournament);
		tournament2.addGame(game);
		
		SearchTournamentParam searchParam = new SearchTournamentParam();
		searchParam.setGame(game.getKey());		
		List<Tournament> tournamentsList = DBManager.searchTournament(searchParam);
		
		assertEquals("searchTournamentTeamTest", "nameTournament", tournamentsList.get(0).getName());
		assertEquals("searchTournamentTeamTest", "123 rue test", tournamentsList.get(0).getAddress());
		assertEquals("searchTournamentTeamTest", "Sherbrooke", tournamentsList.get(0).getCity());
		assertEquals("searchTournamentTeamTest", "Canada", tournamentsList.get(0).getCountry());
		
		assertEquals("searchTournamentTeamTest", "nameTournament2", tournamentsList.get(1).getName());
		assertEquals("searchTournamentTeamTest", "456 rue test", tournamentsList.get(1).getAddress());
		assertEquals("searchTournamentTeamTest", "Farnham", tournamentsList.get(1).getCity());
		assertEquals("searchTournamentTeamTest", "Russie", tournamentsList.get(1).getCountry());	
	}
	
/*********************************************************************************************************************************
 * Game test
 ********************************************************************************************************************************/
	
//	@Test
//	public void getGamesForTournamentTest()
//	{
//		initGames();
//		initTournament();
//		
//		tournament.addGame(game);
//		game2.addTournament(tournament);
//
//		List<Game> gamesList = DBManager.getGamesForTournament(tournament);
//		
//		assertEquals("getGamesForTournamentTest", new Date(1000), gamesList.get(0).getGameDate());
//		assertEquals("getGamesForTournamentTest", new Date(2000), gamesList.get(1).getGameDate());	
//	}
	
	
	@Test
	public void getGameTest()
	{
		initGame();
		initTeams();
		
		List<Key> teams = new ArrayList<Key>();
		teams.add(team.getKey());
		teams.add(team2.getKey());
		//game.setTeams(teams);
		//game.populateTeams(teams);
		DBManager.save(game);
		
		SearchGameParam gameParam = new SearchGameParam();
		gameParam.setTeam(team.getKey());
		
		List<Game> newGames = DBManager.searchGames(gameParam);
		
		//assertEquals("getGameTest team1", team.getKey(), newGames.get(0).getTeams().get(0));
		//assertEquals("getGameTest team2", team2.getKey(), newGames.get(0).getTeams().get(1));
		
	}
	
/*********************************************************************************************************************************
 * Group test
 ********************************************************************************************************************************/
	
	@Test
	public void getGroupTest()
	{
		initGroup();
		
		Group newGroup = DBManager.getGroup(group.getKey());
		
		assertEquals("getGroupTest", group.getName(), newGroup.getName());
	}
	
	@Test
	public void getGroupRoleTest()
	{
		initGroup();
		GroupRole groupRole = new GroupRole();
		groupRole.setRole(Role.Player);
		group.addGroupRoles(groupRole);
		
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setGroup(group.getKey());		
		assertEquals("getGroupRoleTest", Role.Player, DBManager.searchGroupRole(param).get(0).getRole());
	}
	
	@Test
	public void searchGroupTest()
	{
		initGroups();
		
		SearchGroupParam searchParam = new SearchGroupParam();
		List<Group> groups = DBManager.searchGroups(searchParam);		
		
		
		assertEquals("getSearchGroup group1", group.getKey(), groups.get(0).getKey());	
		assertEquals("getSearchGroup group2", group2.getKey(), groups.get(1).getKey());	
	}
	
	@Test
	public void searchGroupQuestionTest()
	{
		initGroup();
		initQuestions();
		group.addQuestion(questionGeneral);
		
		SearchGroupParam searchParam = new SearchGroupParam();
		searchParam.setQuestion(questionGeneral.getKey());
		
		List<Group> groups = DBManager.searchGroups(searchParam);
		
		assertEquals("getSearchGroup question", questionGeneral.getKey(), DBManager.getGroup(groups.get(0).getKey()).getQuestions().get(0));	
	}
	
/*********************************************************************************************************************************
 * GroupRole test
 ********************************************************************************************************************************/
	
	@Test
	public void getRoleTest()
	{
		initGroupRole();
		
		GroupRole newGroupRole = DBManager.getGroupRole(groupRole.getKey());
		
		assertEquals("getRoleTest", Role.Manager, newGroupRole.getRole());	
	}
	
	@Test
	public void getGroupRoleForUserTest()
	{
		initUser();
		initGroup();
		initGroupRoles();
		
		groupRole2.setGroup(group.getKey());
		
		user.addGroupRoles(groupRole);
		user.addGroupRoles(groupRole2);
		
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setUser(user.getKey());
		List<GroupRole> groupRoleList = DBManager.searchGroupRole(param);
		
		assertEquals("getGroupRoleForUserTest groupRole Role", Role.Manager, groupRoleList.get(0).getRole());		
		assertEquals("getTeamsForGroupTest groupRole group name", "groupTest", DBManager.getGroup(groupRoleList.get(1).getGroup()).getName());	
	}
	
	@Test
	public void getGroupRoleForGroupTest()
	{
		initGroup();
		initGroupRoles();
		
		group.addGroupRoles(groupRole);
		group.addGroupRoles(groupRole2);

		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setGroup(group.getKey());
		List<GroupRole> groupRoleList = DBManager.searchGroupRole(param);
		
		assertEquals("getGroupRoleForUserTest groupRole Role", Role.Manager, groupRoleList.get(0).getRole());		
		assertEquals("getTeamsForGroupTest groupRole group name", "groupTest", DBManager.getGroup(groupRoleList.get(1).getGroup()).getName());	
	}
	
/*********************************************************************************************************************************
 * Question test
 ********************************************************************************************************************************/
	
	@Test
	public void getQuestionTest()
	{
		initQuestions();
		
		QuestionGeneral newQuestionGeneral = (QuestionGeneral) DBManager.getQuestion(questionGeneral.getKey());		
		assertEquals("getQuestionTest questionGeneral", "QuestionGeneral", newQuestionGeneral.getQuestion());
		assertEquals("getQuestionTest reponseGeneral", "ReponseGeneral", newQuestionGeneral.getAnswer());
		
		QuestionAnagram newQuestionAnagram = (QuestionAnagram) DBManager.getQuestion(questionAnagram.getKey());		
		assertEquals("getQuestionTest questionAnagram", "QuestionAnagram", newQuestionAnagram.getAnagram());
		assertEquals("getQuestionTest reponseAnagram", "ReponseAnagram", newQuestionAnagram.getAnswer());
	}
	
	@Test
	public void searchQuestionGroupTest()
	{
		initGroup();
		initQuestions();
		
		group.addQuestion(questionAnagram);
		group.addQuestion(questionGeneral);
		SearchQuestionParam param = new SearchQuestionParam();
		List<Key> groups = new ArrayList<Key>();
		groups.add(group.getKey());
		param.setGroups(groups);
		
		List<Question> questions = DBManager.searchQuestion(param);
		
		QuestionAnagram newQuestionAnagram = ((QuestionAnagram)questions.get(0));
		QuestionGeneral newQuestionGeneral = ((QuestionGeneral)questions.get(1));

		assertEquals("searchQuestionGroupTest questionGeneral", "QuestionGeneral", newQuestionGeneral.getQuestion());
		assertEquals("searchQuestionGroupTest questionAnagram", "QuestionAnagram", newQuestionAnagram.getAnagram());
	}
	
	@Test
	public void getQuestionListForQuestionSetSectionTest()
	{
		
	}
	
	@Test
	public void getQuestionSetTest()
	{
		
	}
}
