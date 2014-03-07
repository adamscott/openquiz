package ca.openquiz.webservice.tests;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.junit.Test;

import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Group;
import ca.openquiz.webservice.model.GroupRole;
import ca.openquiz.webservice.model.Team;
import ca.openquiz.webservice.model.Tournament;
import ca.openquiz.webservice.parameter.SearchGameParam;
import ca.openquiz.webservice.parameter.SearchGroupParam;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;
import ca.openquiz.webservice.parameter.SearchTournamentParam;

public class GroupTest extends BaseTest{
	
	@Test
	public void setNameTest()
	{
		initGroup();
		
		Group newGroup = DBManager.getGroup(group.getKey());
		newGroup.setName("detachable");
		DBManager.save(newGroup);
		
		newGroup = DBManager.getGroup(group.getKey());
		
		assertEquals("setNameTest", "detachable", newGroup.getName());
	}

	@Test
	public void addGroupRolesTest()
	{
		initGroup();
		GroupRole groupRole = new GroupRole();

		group.addGroupRoles(groupRole);
		Group newGroup = DBManager.getGroup(group.getKey());
		
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setGroup(newGroup.getKey());
		GroupRole newgroupeRole = DBManager.getGroupRole(DBManager.searchGroupRole(param).get(0).getKey());
		
		assertEquals("addGroupRolesTest", groupRole.getKey(), newgroupeRole.getKey());
	}
	
	@Test
	public void removeGroupRolesTest()
	{
		initGroup();
		GroupRole groupRole = new GroupRole();
		
		group.addGroupRoles(groupRole);
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setGroup(group.getKey());
		assertEquals("removeGroupRolesTest", 1, DBManager.searchGroupRole(param).size());
		
		group.removeGroupRoles(groupRole);
		Group newGroup = DBManager.getGroup(group.getKey());
		param.setGroup(newGroup.getKey());
		assertEquals("removeGroupRolesTest", 0, DBManager.searchGroupRole(param).size());
	}

	@Test(expected=WebApplicationException.class)
	public void deleteGroupRoleTest()
	{
		initGroup();
		GroupRole groupRole = new GroupRole();
		GroupRole groupRole2 = new GroupRole();
		
		group.addGroupRoles(groupRole);
		group.addGroupRoles(groupRole2);
		
		group.delete();
		
		assertNull("deleteGroupRoleTest user exist", group.getKey());
		
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setGroup(group.getKey());
		assertEquals("deleteGroupRoleTest groupRole size", 0, DBManager.searchGroupRole(param).size());

		assertNull("deleteGroupRoleTest groupRole", DBManager.getGroupRole(groupRole.getKey()));
		assertNull("deleteGroupRoleTest groupRole2", DBManager.getGroupRole(groupRole2.getKey()));
	}
	
	@Test
	public void addTeamTest()
	{
		initGroup();
		initTeam();
		
		group.addTeam(team);
				
		Group newGroup = DBManager.getGroup(group.getKey());
		
		Team newTeam = DBManager.getTeam(newGroup.getTeams().get(0));
		
		assertEquals("addTeamTest", "nameTest", newTeam.getName());
	}
	
	@Test
	public void removeTeamTest()
	{
		initGroup();
		initTeam();
		
		group.addTeam(team);
		assertEquals("removeTeamTest", 1, group.getTeams().size());
		
		group.removeTeam(team);
		Group newGroup = DBManager.getGroup(group.getKey());
		
		assertEquals("removeTeamTest", 0, newGroup.getTeams().size());
	}
	
	@Test
	public void addQuestionTest()
	{
		initGroup();
		initQuestions();
		group.addQuestion(questionAnagram);
		group.addQuestion(questionGeneral);
		
		SearchGroupParam param = new SearchGroupParam();
		param.setQuestion(questionAnagram.getKey());
		
		List<Group> groups = DBManager.searchGroups(param);
		assertEquals("addQuestionTest Anagram", questionAnagram.getKey(), groups.get(0).getQuestions().get(0));
		
		param.setQuestion(questionGeneral.getKey());
		groups = DBManager.searchGroups(param);
		assertEquals("addQuestionTest General", questionGeneral.getKey(), groups.get(0).getQuestions().get(1));
	}
	
	@Test
	public void removeQuestionTest()
	{
		//TODO
	}
	
	@Test
	public void addTournament()
	{
		initGroup();
		initTournament();
		
		group.addTournament(tournament);
				
		Group newGroup = DBManager.getGroup(group.getKey());
		
		Tournament newTournament = DBManager.getTournament(newGroup.getTournaments().get(0));
		
		assertEquals("addTournamentTest", "nameTournament", newTournament.getName());
	}
	
	@Test
	public void removeTournament()
	{
		initGroup();
		initTournament();
		
		group.addTournament(tournament);
		assertEquals("removeTournament", 1, group.getTournaments().size());
		
		group.removeTournament(tournament);
		Group newGroup = DBManager.getGroup(group.getKey());
		
		assertEquals("removeTournament", 0, newGroup.getTournaments().size());
	}
	
	@Test
	public void deleteTeamTest()
	{
		initGroup();
		initTeams();
		
		group.addTeam(team);
		group.addTeam(team2);
		
		group.delete();
		
		assertNull("deleteTeamTest group exist", group.getKey());
		assertEquals("deleteTeamTest team size", 0, group.getTeams().size());
		
		assertNull("deleteTeamTest team", DBManager.getTeam(team.getKey()).getGroup());
		assertNull("deleteTeamTest team2", DBManager.getTeam(team2.getKey()).getGroup());
	}
	
	@Test
	public void deleteTournamentTest()
	{
		initGroup();
		initTournament();		
		group.addTournament(tournament);
		assertNotNull("deleteGameTest", DBManager.getTournament(tournament.getKey()).getGroup());
		
		group.delete();
		
		assertNull("deleteGameTest", DBManager.getTournament(tournament.getKey()).getGroup());
	}
}
