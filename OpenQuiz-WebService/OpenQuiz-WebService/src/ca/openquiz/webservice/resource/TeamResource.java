package ca.openquiz.webservice.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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

import ca.openquiz.webservice.parameter.SearchTeamParam;
import ca.openquiz.webservice.response.BaseResponse;
import ca.openquiz.webservice.response.KeyResponse;
import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.manager.security.RoleManager;
import ca.openquiz.webservice.model.Group;
import ca.openquiz.webservice.model.Team;
import ca.openquiz.webservice.model.User;

@Path("/teams")
public class TeamResource {


	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Team> getTeamList(
			@QueryParam("group") String group, 
			@DefaultValue("true")@QueryParam("active") boolean active,
			@QueryParam("page") int pageNumber, 
			@QueryParam("max") int resultsByPage) 
			{
		SearchTeamParam searchParam = new SearchTeamParam();
		if(group != null)
			searchParam.setGroup(KeyFactory.stringToKey(group));

		searchParam.setPageNumber(pageNumber);
		searchParam.setResultsByPage(resultsByPage);
		searchParam.setActive(active);

		return DBManager.searchTeams(searchParam);
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Team getTeamById(@PathParam("id") String id) 
	{
		Key key = KeyFactory.stringToKey(id);
		Team team = null;

		try {
			team = DBManager.getTeam(key);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return team;
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addTeam(@Context SecurityContext sc, Team team)
	{
		if(!team.isValid()){
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		KeyResponse resp = new KeyResponse();

		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.Manager);

		List<Key> groupKeys = new ArrayList<Key>();
		Key group = team.getGroup();
		groupKeys.add(group);

		if(RoleManager.userRoleMatchAllGroups(sc, roleList, groupKeys, true)){
			try {
				DBManager.save(team);
				team.setGroup(group);
				resp.setKey(KeyFactory.keyToString(team.getKey()));
			} 
			catch(Exception e) {
				resp.setError(e.getMessage());
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}

		return resp;
	}

	@PUT
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editTeam(@Context SecurityContext sc, @PathParam("id") String id, Team team) {

		if(!team.isValid()){
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		BaseResponse resp = new BaseResponse();

		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.Manager);

		List<Key> groupKeys = new ArrayList<Key>();
		Key group = team.getGroup();
		groupKeys.add(group);

		if(RoleManager.userRoleMatchAllGroups(sc, roleList, groupKeys, true)){

			Key key = KeyFactory.stringToKey(id);
			try {
				Team tmpTeam = DBManager.getTeam(key);

				tmpTeam.setGroup(team.getGroup());
				tmpTeam.setName(team.getName());

				for(Key uKey : tmpTeam.getUsers()){
					if(!team.getUsers().contains(uKey)){
						User u = DBManager.getUser(uKey);
						tmpTeam.removeUser(u);
					}
				}

				for(Key uKey : team.getUsers()){
					User u = DBManager.getUser(uKey);
					if(!u.getTeams().contains(tmpTeam) || !tmpTeam.getUsers().contains(u)) {
						u.addTeam(tmpTeam);
					}
				}
				tmpTeam.setActive(team.isActive());

				DBManager.save(tmpTeam);

				return resp;
			}
			catch(Exception e) {
				resp.setError(e.getMessage());
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}

		return resp;
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse removeTeam(@Context SecurityContext sc, @PathParam("id") String id) {
		BaseResponse resp = new BaseResponse();

		try{
			Key key = KeyFactory.stringToKey(id);
			Team team = DBManager.getTeam(key);

			List<Role> roleList = new ArrayList<Role>();
			roleList.add(Role.Manager);

			List<Key> groupKeys = new ArrayList<Key>();
			groupKeys.add(team.getGroup());

			if(RoleManager.userRoleMatchGroup(sc, roleList, groupKeys, true)){
				Group g = DBManager.getGroup(team.getGroup());
				g.removeTeam(team);
				return resp;
			}
		}
		catch(Exception e)
		{
			resp.setError(e.getMessage());
			return resp;
		}
		return resp;
	}
}
