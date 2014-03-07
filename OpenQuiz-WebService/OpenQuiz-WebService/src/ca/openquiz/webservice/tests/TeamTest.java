package ca.openquiz.webservice.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Group;
import ca.openquiz.webservice.model.Team;
import ca.openquiz.webservice.model.Tournament;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.parameter.SearchUserParam;

public class TeamTest extends BaseTest{
	
	@Test
	public void setNameTest()
	{
		initTeam();
		
		Team newTeam = DBManager.getTeam(team.getKey());
		newTeam.setName("detachable");
		DBManager.save(newTeam);
		
		newTeam = DBManager.getTeam(team.getKey());
		
		assertEquals("setNameTest", "detachable", newTeam.getName());
	}

	@Test
	public void addUserTest()
	{
		initUser();
		initTeam();
		
		team.addUser(user);
				
		Team newTeam = DBManager.getTeam(team.getKey());
		
		SearchUserParam param = new SearchUserParam();
		param.setTeam(newTeam.getKey());
		User newUser = DBManager.searchUsers(param).get(0);
		
		assertEquals("addUserTest", "FirstNameTest", newUser.getFirstName());
	}
	
	@Test
	public void removeUserTest()
	{
		initUsers();
		initTeam();
		
		team.addUser(user);
		
		assertEquals("removeUserTest", 1, team.getUsers().size());
		
		team.removeUser(user);
		
		Team newTeam = DBManager.getTeam(team.getKey());
		
		assertEquals("removeTeamTest", 0, newTeam.getUsers().size());
	}
	
	@Test
	public void deleteTeamTest()
	{
		initUsers();
		initTeam();
		
		team.addUser(user);
		team.addUser(user2);

		team.delete();
		
		assertEquals("deleteTeamTest user isActive", false, team.isActive());
	}
	
	@Test
	public void addGroupTest()
	{
		initTeam();
		initGroup();
		
		team.setGroup(group);
		
		Team newTeam = DBManager.getTeam(team.getKey());
		Group newGroup = DBManager.getGroup(newTeam.getGroup());
		
		assertEquals("addGroupTest", "groupTest", newGroup.getName());
	}
	
	@Test
	public void replaceGroupTest()
	{
		initTeam();
		initGroups();
		
		team.setGroup(group);
		team.setGroup(group2);
		
		Team newTeam = DBManager.getTeam(team.getKey());
		Group newGroup = DBManager.getGroup(newTeam.getGroup());
		
		assertEquals("replaceGroupTest", "groupTest2", newGroup.getName());
	}
	
	@Test
	public void removeGroupTest()
	{
		initTeam();
		initGroup();
		
		team.setGroup(group);
		
		team.setGroup((Group)null);
		
		DBManager.getTeam(team.getKey());
		assertNull("removeGroupTest", team.getGroup());
	}
	
	@Test
	public void addTournamentTest()
	{
		initTeam();
		initTournament();
		
		team.addTournament(tournament);
		
		DBManager.getTeam(team.getKey());
		Tournament newTournament = DBManager.getTournament(team.getTournaments().get(0));
		
		assertEquals("addTournamentTest", "nameTournament", newTournament.getName());
	}
	
	@Test
	public void removeTournamentTest()
	{
		initTournament();
		initTeam();
		
		team.addTournament(tournament);
		team.removeTournament(tournament);
		
		DBManager.getTeam(team.getKey());
		
		assertEquals("removeTournamentTest", 0, team.getTournaments().size());
	}
}
