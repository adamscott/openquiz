package ca.openquiz.webservice.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Game;
import ca.openquiz.webservice.parameter.SearchGameParam;
import com.google.appengine.api.datastore.Key;
public class GameTest extends BaseTest{
	
	@Test
	public void deleteTest()
	{
//		test delete on tournament if necessary
//		test delete on questionSet if necessary
		
		initGame();
		initTeams();
		initUsers();
		
		List<Key> team1Players = new ArrayList<Key>();
		team1Players.add(user.getKey());
		
		List<Key> team2Players = new ArrayList<Key>();
		team1Players.add(user.getKey());
		
		game.delete();
		
		SearchGameParam param = new SearchGameParam();
		param.setTeam(team.getKey());
		param.setActive(false);
		assertFalse("deleteTest", DBManager.searchGames(param).get(0).isActive());
	}
	
	@Test
	public void copyTest()
	{
		initGame();
		initTeams();
		initUsers();
		
		game.setName("gameName");
		
		List<Key> teams = new ArrayList<Key>();
		teams.add(team.getKey());
		teams.add(team2.getKey());
		//game.setTeams(teams);

		Game newGame = new Game();
		newGame.copy(game);
		
		assertEquals("copyTest gameDate", game.getGameDate(), newGame.getGameDate());
		assertEquals("copyTest name", game.getName(), newGame.getName());
		//assertEquals("copyTest team1", game.getTeams().get(0), newGame.getTeams().get(0));
		//assertEquals("copyTest team2", game.getTeams().get(1), newGame.getTeams().get(1));
		
	}
	
	@Test
	public void setTeamsTest()
	{
		initGame();
		initTeams();
		initUsers();
		
		//game.populateTeams(teams);
		
		Game newGame = DBManager.getGame(game.getKey());
		//assertEquals("setTeamsTest game", team.getName(), DBManager.getTeam(newGame.getTeams().get(0)).getName());
		//assertEquals("setTeamsTest game2", team2.getName(), DBManager.getTeam(newGame.getTeams().get(1)).getName());
		
		assertEquals("setTeamsTest team1", newGame.getKey(), DBManager.getTeam(team.getKey()).getGames().get(0));
		assertEquals("setTeamsTest team2", newGame.getKey(), DBManager.getTeam(team2.getKey()).getGames().get(0));
	}

	@Test
	public void addTournamentTest()
	{
		initTournament();
		initGame();
		
		game.addTournament(tournament);
		Game newGame = DBManager.getGame(game.getKey());
		
		assertEquals("addTournamentTest", "nameTournament", DBManager.getTournament(newGame.getTournament()).getName());
	}
	
	@Test
	public void removeTournamentTest()
	{
		initTournament();
		initGame();
		
		game.addTournament(tournament);		
		assertNotNull("removeTournamentTest added", DBManager.getGame(game.getKey()).getTournament());
		
		game.removeTournament();
		assertNull("removeTournamentTest removed", DBManager.getGame(game.getKey()).getTournament());
	}
	
	@Test
	public void setQuestionSetTest()
	{
//		TODO
	}
	
}
