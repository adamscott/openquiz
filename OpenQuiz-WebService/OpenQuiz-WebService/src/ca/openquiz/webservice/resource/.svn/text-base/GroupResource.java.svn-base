package ca.openquiz.webservice.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import ca.openquiz.webservice.parameter.SearchGroupParam;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;
import ca.openquiz.webservice.response.BaseResponse;
import ca.openquiz.webservice.response.KeyResponse;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.manager.security.RoleManager;
import ca.openquiz.webservice.model.Group;
import ca.openquiz.webservice.model.GroupRole;
import ca.openquiz.webservice.model.Team;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.enums.Role;

@Path("/groups")
public class GroupResource {
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Group> getGroupList(
			@QueryParam("page") int pageNumber, 
			@QueryParam("max") int resultsByPage) 
			{
		SearchGroupParam searchParam = new SearchGroupParam();
		searchParam.setPageNumber(pageNumber);
		searchParam.setResultsByPage(resultsByPage);

		return new ArrayList<Group>(DBManager.searchGroups(searchParam));
			}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Group getGroupById(@PathParam("id") String id) 
	{
		Key key = KeyFactory.stringToKey(id);
		Group group = null;

		try {
			group = DBManager.getGroup(key);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return group;
	}

	@GET
	@Path("roles")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<GroupRole> getGroupRoles(@Context SecurityContext sc) 
	{
		List<GroupRole> groupsResp = new ArrayList<GroupRole>();

		List<Role> roles = new ArrayList<Role>();
		roles.add(Role.Manager);

		List<Key> groupKeys = RoleManager.getUserGroups(sc, roles);

		for(Key k : groupKeys) {
			SearchGroupRoleParam param = new SearchGroupRoleParam();
			param.setResultsByPage(0);
			param.setPageNumber(0);
			param.setGroup(k);
			groupsResp.addAll(DBManager.searchGroupRole(param));
		}

		if(RoleManager.isUserAdmin(sc)) {
			SearchGroupRoleParam param = new SearchGroupRoleParam();
			param.setRole(Role.Manager);
			param.setResultsByPage(0);
			param.setPageNumber(0);
			for(GroupRole gr : DBManager.searchGroupRole(param)) {
				if(!groupsResp.contains(gr)){
					groupsResp.add(gr);
				}
			}
		}

		return groupsResp;
	}


	@POST
	@Path("role")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addGroupRole(@Context SecurityContext sc, GroupRole iGroupRole)
	{
		KeyResponse resp = new KeyResponse();
		
		if(iGroupRole == null || iGroupRole.getUser() == null
			|| iGroupRole.getGroup() == null || iGroupRole.getRole() == null)
			throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
		
		// Security check
		User usr = RoleManager.getCurrentUser(sc);
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setUser(usr.getKey());
		param.setGroup(iGroupRole.getGroup());
		List<GroupRole> groupRoleList = DBManager.searchGroupRole(param);
		
		boolean userAuthorized = false;
		if(groupRoleList != null){
			for(GroupRole gr : groupRoleList)
			{
				if(gr.getRole() == Role.Manager)
				{
					userAuthorized = true;
					break;
				}
			}
		}
		
		if(userAuthorized)
		{
			DBManager.save(iGroupRole);
			resp.setKey(KeyFactory.keyToString(iGroupRole.getKey()));
		}
		else
			throw new WebApplicationException(Response.Status.FORBIDDEN);

		return resp;
	}

	@PUT
	@Path("role")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editGroupRole(@Context SecurityContext sc, GroupRole iGroupRole)
	{
		BaseResponse response = new BaseResponse();
		
		if(iGroupRole == null || iGroupRole.getUser() == null
			|| iGroupRole.getGroup() == null || iGroupRole.getRole() == null)
		{
			response.setError("GroupRole provided is invalid");
			return response;
		}
		
		// Security check
		User usr = RoleManager.getCurrentUser(sc);
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setUser(usr.getKey());
		param.setGroup(iGroupRole.getGroup());
		List<GroupRole> groupRoleList = DBManager.searchGroupRole(param);
		
		boolean userAuthorized = false;
		if(groupRoleList != null){
			for(GroupRole gr : groupRoleList)
			{
				if(gr.getRole() == Role.Manager)
				{
					userAuthorized = true;
					break;
				}
			}
		}
		
		if(userAuthorized)
		{
			// Get the group and update it
			param.setUser(iGroupRole.getUser());
			param.setGroup(iGroupRole.getGroup());
			groupRoleList = DBManager.searchGroupRole(param);

			GroupRole groupRole = groupRoleList.size() >= 1 ? groupRoleList.get(0) : null;
			
			if(groupRole != null)
			{
				groupRole.setRole(iGroupRole.getRole());
				DBManager.save(groupRole);
			}
			else
				response.setError("Could not find the groupRole");
		}
		else
			response.setError("User is not authorized");

		return response;
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addGroup(@Context SecurityContext sc, Group group)
	{
		KeyResponse resp = new KeyResponse();

		if(RoleManager.isUserAdmin(sc)) {
			if(group.getName() != null && !group.getName().isEmpty()){
				try {
					DBManager.save(group);
					resp.setKey(KeyFactory.keyToString(group.getKey()));
					DBManager.save(new GroupRole(RoleManager.getCurrentUser(sc), group, Role.Manager));
				} 
				catch(Exception e) {
					resp.setError(e.getMessage());
				}
			}
			else {
				throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
			}
		}
		else {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
		return resp;
	}

	//	TODO deleteTeam
	@POST
	@Path("{id}/addTeam")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse addTeamToUser(@PathParam("id") String groupId, @QueryParam("team") String teamId) {
		BaseResponse response = new BaseResponse();
		Group group = null;
		Team team = null;

		if(groupId != null && !groupId.isEmpty())
			group = DBManager.getGroup(KeyFactory.stringToKey(groupId));
		else
			response.setError("Group parameter is invalid");
		if(teamId != null && !teamId.isEmpty())
			team = DBManager.getTeam(KeyFactory.stringToKey(teamId));
		else
			response.setError("Team parameter is invalid");

		group.addTeam(team);

		return response;
	}

	@PUT
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editGroup(@Context SecurityContext sc, @PathParam("id") String id, Group group) {

		if(RoleManager.isUserAdmin(sc)){	
			BaseResponse resp = new BaseResponse();
			Key key = KeyFactory.stringToKey(id);
			try {
				Group tmpGroup = DBManager.getGroup(key);

				tmpGroup.setName(group.getName());

				DBManager.save(tmpGroup);

				return resp;
			}
			catch(Exception e) {
				resp.setError(e.getMessage());
				return resp;
			}	
		}
		else {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse removeGroup(@Context SecurityContext sc, @PathParam("id") String id) {
		BaseResponse resp = new BaseResponse();

		try{
			Key key = KeyFactory.stringToKey(id);
			Group group = DBManager.getGroup(key);
			
			List<Role> roleList = new ArrayList<Role>();
			roleList.add(Role.Manager);
			
			List<Key> groupList = new ArrayList<Key>();
			groupList.add(group.getKey());
			
			if(RoleManager.isUserAdmin(sc) || RoleManager.userRoleMatchGroup(sc, roleList, groupList, true)){
				group.delete();
				return resp;
			}
			else{
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		catch(Exception e)
		{
			resp.setError(e.getMessage());
			return resp;
		}
	}
}
