package ca.openquiz.webservice.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Game;
import ca.openquiz.webservice.model.Group;
import ca.openquiz.webservice.model.Team;
import ca.openquiz.webservice.model.Tournament;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.parameter.SearchGameParam;
import ca.openquiz.webservice.parameter.SearchGroupParam;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;
import ca.openquiz.webservice.parameter.SearchTournamentParam;
import ca.openquiz.webservice.parameter.SearchUserParam;

public class TournamentTest extends BaseTest{

	@Test
	public void addTeamTest()
	{
		initTournament();
		initTeam();
		
		tournament.addTeam(team);
		
		Tournament newTournament = DBManager.getTournament(tournament.getKey());
		Team newTeam = DBManager.getTeam(newTournament.getTeams().get(0));
		
		assertEquals("addTeamTest", "nameTest", newTeam.getName());
	}
	
	@Test
	public void removeTeamTest()
	{
		initTournament();
		initTeam();
		
		tournament.addTeam(team);

		SearchTournamentParam param = new SearchTournamentParam();
		param.setTeam(team.getKey());
		assertEquals("removeTeamTest initialized", 1, DBManager.searchTournament(param).size());
		
		tournament.removeTeam(team);
		assertEquals("removeTeamTest removed", 0, DBManager.searchTournament(param).size());
	}	
	
	@Test
	public void addGameTest()
	{
		initTournament();
		initGame();
		
		tournament.addGame(game);
		
		Tournament newTournament = DBManager.getTournament(tournament.getKey());
		Game newGame = DBManager.getGame(newTournament.getGames().get(0));
		
		assertEquals("addGameTest", "GameTest", newGame.getName());
	}
	
	@Test
	public void removeGameTest()
	{
		initTournament();
		initGame();
		
		tournament.addGame(game);

		SearchTournamentParam param = new SearchTournamentParam();
		param.setGame(game.getKey());
		assertEquals("removeGameTest initialized", 1, DBManager.searchTournament(param).size());
		
		tournament.removeGame(game);
		assertEquals("removeGameTest removed", 0, DBManager.getTournament(tournament.getKey()).getGames().size());
	}
	
	@Test
	public void deleteTeamTest()
	{
		initTeam();
		initTournament();
		
		tournament.addTeam(team);
		tournament.delete();
		
		Team newTeam = DBManager.getTeam(team.getKey());
		
		assertEquals("deleteTeamTest", 0, newTeam.getTournaments().size());
	}
	
	@Test
	public void deleteGameTest()
	{
		initGame();
		initTournament();		
		tournament.addGame(game);
		
		SearchGameParam param = new SearchGameParam();
		param.setTournament(tournament.getKey());

		tournament.delete();
		
		assertEquals("deleteGameTest", 0, DBManager.searchGames(param).size());
	}
	
	@Test
	public void deleteGroupTest()
	{
		initTournament();
		initGroup();
		
		tournament.addGroup(group);
		tournament.delete();
		
		Group newGroup = DBManager.getGroup(group.getKey());
		
		assertEquals("deleteGroupTest", 0, newGroup.getTournaments().size());
	}
	
//	@Test
	public void deleteTest()
	{
		//TODO fix all delete first
	}
		
}
