package ca.openquiz.webservice.tests;

import static org.junit.Assert.*;

import javax.ws.rs.WebApplicationException;

import org.junit.Test;

import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.GroupRole;
import ca.openquiz.webservice.model.Team;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;

public class UserTest extends BaseTest{

	@Test 
	public void createUserUniqueEmailTest()
	{
		String email = "test@email.com";
		initUser();	//test@email.com
		
		User emailUser = new User();
		emailUser.setEmail(email);
		DBManager.save(emailUser);
		
		User newUser = DBManager.getUser(emailUser.getKey());
		assertEquals("createUserUniqueEmailTest unique", email, newUser.getEmail());
		
		newUser = DBManager.getUser(user.getKey());
		assertEquals("createUserUniqueEmailTest", email, newUser.getEmail());
	}
	
	@Test
	public void setNameTest()
	{
		initUser();
		
		User newUser = DBManager.getUser(user.getKey());
		newUser.setFirstName("detachable");
		DBManager.save(newUser);
		
		newUser = DBManager.getUser(user.getKey());
		
		assertEquals("setNameTest", "detachable", newUser.getFirstName());
	}
	
	@Test
	public void addTeamTest()
	{
		initUser();
		initTeam();
		initGroup();
		
		new GroupRole(user, group, Role.Player);
		team.setGroup(group);
		user.addTeam(team);
		
		User newUser = DBManager.getUser(user.getKey());
		
		Team newTeam = DBManager.getTeam(newUser.getTeams().get(0));
		
		assertEquals("addTeamTest", "nameTest", newTeam.getName());
	}
	
	@Test
	public void removeTeamTest()
	{
		initUser();
		initTeam();
		initGroup();
		team.setGroup(group);
		new GroupRole(user, group, Role.Player);
		
		user.addTeam(team);
		assertEquals("removeTeamTest", 1, user.getTeams().size());
		
		user.removeTeam(team);
		User newUser = DBManager.getUser(user.getKey());
		
		assertEquals("removeTeamTest", 0, newUser.getTeams().size());
	}
	
	@Test
	public void setEmailTest()
	{
		initUser();
		User newUser = DBManager.getUser(user.getKey());
		newUser.setEmail("detachableEmail");
		DBManager.save(newUser);
		newUser = DBManager.getUser(user.getKey());
		
		assertEquals("set Email", "detachableemail", newUser.getEmail());
	}
	
	@Test
	public void addGroupRolesTest()
	{
		initUser();
		GroupRole groupRole = new GroupRole();
		
		user.addGroupRoles(groupRole);
		User newUser = DBManager.getUser(user.getKey());
		
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setUser(newUser.getKey());
		GroupRole newgroupeRole = DBManager.getGroupRole(DBManager.searchGroupRole(param).get(0).getKey());
		
		assertEquals("addGroupRolesTest", user.getKey(), newgroupeRole.getUser());
	}
	
	@Test
	public void removeGroupRolesTest()
	{
		initUser();
		GroupRole groupRole = new GroupRole();
		
		user.addGroupRoles(groupRole);
		
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setUser(user.getKey());
		
		assertEquals("removeGroupRolesTest", 1, DBManager.searchGroupRole(param).size());
		
		user.removeGroupRoles(groupRole);
		User newUser = DBManager.getUser(user.getKey());
		
		param.setUser(newUser.getKey());
		assertEquals("removeGroupRolesTest", 0, DBManager.searchGroupRole(param).size());
	}
	
	@Test
	public void deleteTeamTest()
	{
		initUser();
		initTeams();
		
		user.addTeam(team);
		user.addTeam(team2);

		user.delete();
		
		assertNull("removeTest user exist", user.getKey());
		assertEquals("removeTest team size", 0, user.getTeams().size());
		
		assertEquals("removeTest team", 0, DBManager.getTeam(team.getKey()).getUsers().size());
		assertEquals("removeTest team2", 0, DBManager.getTeam(team2.getKey()).getUsers().size());
	}
	
	@Test(expected=WebApplicationException.class)
	public void deleteGroupRoleTest()
	{
		initUser();
		GroupRole groupRole = new GroupRole();
		GroupRole groupRole2 = new GroupRole();
		
		user.addGroupRoles(groupRole);
		user.addGroupRoles(groupRole2);
		
		user.delete();
		
		assertNull("deleteGroupRoleTest user exist", user.getKey());
		
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setUser(user.getKey());
		assertEquals("deleteGroupRoleTest groupRole size", 0, DBManager.searchGroupRole(param).size());

		assertNull("deleteGroupRoleTest groupRole", DBManager.getGroupRole(groupRole.getKey()));
		assertNull("deleteGroupRoleTest groupRole2", DBManager.getGroupRole(groupRole2.getKey()));
	}
	
	@Test(expected=WebApplicationException.class)
	public void deleteAllTest()
	{
		initUser();
		initTeams();
		GroupRole groupRole = new GroupRole();
		GroupRole groupRole2 = new GroupRole();
		
		user.addTeam(team);
		user.addTeam(team2);
		
		user.addGroupRoles(groupRole);
		user.addGroupRoles(groupRole2);
		
		user.delete();
		
		//Team tests
		assertNull("removeTest user exist", user.getKey());
		assertEquals("removeTest team size", 0, user.getTeams().size());
		
		assertEquals("removeTest team", 0, DBManager.getTeam(team.getKey()).getUsers().size());
		assertEquals("removeTest team2", 0, DBManager.getTeam(team2.getKey()).getUsers().size());
		
		//GroupRole tests
		assertNull("deleteGroupRoleTest user exist", user.getKey());
		
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setUser(user.getKey());
		assertEquals("deleteGroupRoleTest groupRole size", 0, DBManager.searchGroupRole(param).size());

		assertNull("deleteGroupRoleTest groupRole", DBManager.getGroupRole(groupRole.getKey()));
		assertNull("deleteGroupRoleTest groupRole2", DBManager.getGroupRole(groupRole2.getKey()));
	}

	@Test
	public void addTeamInGroupTest()
	{
		initUser();
		initTeam();
		initGroup();
		initGroupRole();
		groupRole.setGroup(group.getKey());
		
		user.addGroupRoles(groupRole);
		
		team.setGroup(group);
		user.addGroupRoles(groupRole);
		
		assertTrue("addTeamInGroupTest", user.addTeam(team));
	}
	
	@Test
	public void addTeamNotInGroupTest()
	{
		initUser();
		initTeam();
		initGroups();
		initGroupRole();
		groupRole.setGroup(group.getKey());
		
		user.addGroupRoles(groupRole);
		
		team.setGroup(group2);
		user.addGroupRoles(groupRole);
		
		assertFalse("addTeamInGroupTest", user.addTeam(team));
	}
	
	@Test
	public void removeGroupRoleCheckTeamTest()
	{
		initUser();
		initTeam();
		initGroup();
		GroupRole groupRole = new GroupRole(user, group, Role.Player);
		
		team.setGroup(group);
		user.addTeam(team);
		
		assertEquals("removeGroupRoleCheckTeamTest", 1, DBManager.getTeam(team.getKey()).getUsers().size());
		
		user.removeGroupRoles(groupRole);
		assertEquals("removeGroupRoleCheckTeamTest", 0, DBManager.getTeam(team.getKey()).getUsers().size());
	}

}
