package ca.openquiz.webservice.tests;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.junit.Test;

import com.google.appengine.api.datastore.Key;

import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.GroupRole;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;
import ca.openquiz.webservice.parameter.SearchUserParam;

public class RoleTest extends BaseTest{

	@Test
	public void setRoleTest()
	{
		initGroupRole();
		
		GroupRole newGroupRole = DBManager.getGroupRole(groupRole.getKey());
		
		assertEquals("set Email", Role.Manager, newGroupRole.getRole());
	}
	
	@Test
	public void groupRoleTest()
	{
		initUser();
		initGroup();
		GroupRole groupRole = new GroupRole(user, group, Role.Manager);
		
		GroupRole newGroupRole = DBManager.getGroupRole(groupRole.getKey());
		
		assertEquals("groupRoleTest user", "FirstNameTest", DBManager.getUser(newGroupRole.getUser()).getFirstName());
		assertEquals("groupRoleTest group", "groupTest", DBManager.getGroup(newGroupRole.getGroup()).getName());
		assertEquals("groupRoleTest Role", Role.Manager, newGroupRole.getRole());
		
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setUser(newGroupRole.getUser());
		assertEquals("groupRoleTest user", groupRole.getKey(), DBManager.searchGroupRole(param).get(0).getKey());
		param.setUser(user.getKey());
		assertEquals("groupRoleTest user", groupRole.getKey(), DBManager.searchGroupRole(param).get(0).getKey());
	}
	
	@Test
	public void queryTest()
	{
		initUsers();
		initGroup();
		GroupRole groupRole = new GroupRole(user, group, Role.Manager);
		
		GroupRole newGroupRole = DBManager.getGroupRole(groupRole.getKey());
		
		SearchUserParam param = new SearchUserParam();
//		param.setLanguage(0);
		param.setGroup(group.getKey());
		List<User> users = DBManager.searchUsers(param);
		
		assertEquals("groupRoleTest user", "FirstNameTest", users.get(0).getFirstName());
//		assertEquals("groupRoleTest group", "groupTest", users.get(0).getName());
//		assertEquals("groupRoleTest Role", Role.Manager, newGroupRole.getRole());
		
//		assertEquals("groupRoleTest user", groupRole.getKey(), DBManager.getUser(newGroupRole.getUser()).getGroupRoles().get(0));
//		assertEquals("groupRoleTest user", groupRole.getKey(), user.getGroupRoles().get(0));
	}
}
