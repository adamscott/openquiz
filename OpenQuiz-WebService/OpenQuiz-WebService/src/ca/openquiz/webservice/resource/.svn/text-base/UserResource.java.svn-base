package ca.openquiz.webservice.resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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

import ca.openquiz.webservice.parameter.CreateUserParam;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;
import ca.openquiz.webservice.parameter.SearchUserParam;
import ca.openquiz.webservice.response.BaseResponse;
import ca.openquiz.webservice.response.KeyResponse;
import ca.openquiz.webservice.response.UserListResponse;
import ca.openquiz.webservice.response.UserResponse;
import ca.openquiz.webservice.tools.ExceptionLogger;
import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.manager.security.RoleManager;
import ca.openquiz.webservice.manager.security.SessionManager;
import ca.openquiz.webservice.model.Group;
import ca.openquiz.webservice.model.GroupRole;
import ca.openquiz.webservice.model.Session;
import ca.openquiz.webservice.model.Team;
import ca.openquiz.webservice.model.User;

@Path("/users")
public class UserResource {

	/**
	 * GET rest/users
	 * Get the list of users
	 * @param language Language of the user
	 * @param group Group in which the user has access
	 * @param email Email of the user to get
	 * @param team Filters user by team id
	 * @param pageNumber Number of the page to show
	 * @param resultsByPage Number of elements to be returned for each page
	 * @return The list of users
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserListResponse getUserList(@QueryParam("lang") String language,
			@QueryParam("group") String group, 
			@QueryParam("email") String email,
			@QueryParam("team") String team,
			@QueryParam("page") int pageNumber, 
			@QueryParam("max") int resultsByPage) 	{
		SearchUserParam searchParam = new SearchUserParam();
		if(language != null)
			searchParam.setLanguage(Language.valueOf(language));
		if(group != null)
			searchParam.setGroup(KeyFactory.stringToKey(group));
		if(team != null)
			searchParam.setTeam(KeyFactory.stringToKey(team));
		if(email != null)
			searchParam.setEmail(email);
		
		searchParam.setPageNumber(pageNumber);
		searchParam.setResultsByPage(resultsByPage);

		return new UserListResponse(DBManager.searchUsers(searchParam));
	}

	/**
	 * POST rest/users
	 * Adds a new user to the database
	 * @param user to be added (CreateUserParam)
	 * @return The key of the user created
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addUser(CreateUserParam user)
	{
		KeyResponse resp = new KeyResponse();
		try
		{
			if(!user.isValid()) {
				resp.setError("This user cannot be created");
				return resp;
			}

			if(!DBManager.validateEmail(user.getEmail())) {
				resp.setError("An account with this email is already registered");
				return resp;
			}

			Date d = new Date();
			User newUser = new User();
			newUser.setCreationDate(d);
			newUser.setBirthDate(user.getBirthDate());
			newUser.setEmail(user.getEmail());
			newUser.setFirstName(user.getFirstName());
			newUser.setLastName(user.getLastName());
			newUser.setLanguage(user.getLanguage());
			newUser.setLastLogin(null);
			newUser.setPassword(user.getPassword());
			newUser.setAdmin(false);

			DBManager.save(newUser);
			resp.setKey(KeyFactory.keyToString(newUser.getKey()));
		}
		catch(Exception e)
		{
			resp.setError(e.getMessage());
		}

		return resp;
	}

	/**
	 * POST rest/users/{userId}/addTeam?team={teamId}
	 * Add a user to a team
	 * @param userId User to add
	 * @param teamId Team in which the user is added
	 * @return Completed or error
	 */
	@POST
	@Path("{id}/addTeam")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse addTeamToUser(@PathParam("id") String userId, 
			@QueryParam("team") String teamId) {
		BaseResponse response = new BaseResponse();
		User user = null;
		Team team = null;

		if(userId != null && !userId.isEmpty())
			user = DBManager.getUser(KeyFactory.stringToKey(userId));
		else
			response.setError("User parameter is invalid");
		if(teamId != null && !teamId.isEmpty())
			team = DBManager.getTeam(KeyFactory.stringToKey(teamId));
		else
			response.setError("Team parameter is invalid");

		user.addTeam(team);

		return response;
	}

	/**
	 * POST rest/users/{userId}/removeTeam?team={teamId}
	 * Removes a user from a team
	 * @param userId Id of the user to remove
	 * @param teamId Team in which the user is removed
	 * @return completed or error message
	 */
	@POST
	@Path("{id}/removeTeam")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse removeTeamToUser(@PathParam("id") String userId, @QueryParam("team") String teamId) {
		BaseResponse response = new BaseResponse();
		User user = null;
		Team team = null;

		if(userId != null && !userId.isEmpty())
			user = DBManager.getUser(KeyFactory.stringToKey(userId));
		else
			response.setError("User parameter is invalid");
		if(teamId != null && !teamId.isEmpty())
			team = DBManager.getTeam(KeyFactory.stringToKey(teamId));
		else
			response.setError("Team parameter is invalid");

		user.removeTeam(team);

		return response;
	}

	/**
	 * POST rest/users/{id}/addGroup?group={groupId}&role={roleId}
	 * Add a role for a user in a group
	 * @param sc The authentification of a manager of the group
	 * @param userId The id of the user
	 * @param groupId The id of the group
	 * @param roleId The role to give to the user in this group
	 * @return Completed or Error
	 */
	@POST
	@Path("{id}/addGroup")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse addGroupRoleToUser(@Context SecurityContext sc,
			@PathParam("id") String userId,
			@QueryParam("group") String groupId, 
			@QueryParam("role") String roleId)
	{
		BaseResponse response = new BaseResponse();
		User user = null;
		Group group = null;
		Role role = null;

		if(userId != null && !userId.isEmpty())
			user = DBManager.getUser(KeyFactory.stringToKey(userId));
		else
			response.setError("User parameter is invalid");
		if(groupId != null && !groupId.isEmpty())
			group = DBManager.getGroup(KeyFactory.stringToKey(groupId));
		else
			response.setError("Group parameter is invalid");
		if(roleId != null && !roleId.isEmpty())		
			role = Role.valueOf(roleId);
		else
			response.setError("Role parameter is invalid");

		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.Manager);

		List<Key> groupList = new ArrayList<Key>();
		groupList.add(group.getKey());

		if(RoleManager.userRoleMatchGroup(sc, roleList, groupList, true)){
			GroupRole groupRole = new GroupRole(user, group, role);
			DBManager.save(groupRole);
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}

		return response;

	}

	/**
	 * DELETE rest/users/{userid}/removeGroup/{groupid}
	 * Remove a role for a user in a group
	 * @param sc The authentication of a group's manager or the user itself
	 * @param userId The id of the user
	 * @param groupId The id of the group
	 * @return Completed or Error
	 */
	@DELETE
	@Path("{id}/removeGroup/{groupid}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse deleteGroupRoleToUser(@Context SecurityContext sc,
			@PathParam("id") String userId,
			@PathParam("groupid") String groupId)
	{
		BaseResponse response = new BaseResponse();
		GroupRole groupRole = null;

		if(groupId != null && !groupId.isEmpty() && userId != null && !userId.isEmpty())
		{
			SearchGroupRoleParam param = new SearchGroupRoleParam();
			param.setUser(KeyFactory.stringToKey(userId));
			param.setGroup(KeyFactory.stringToKey(groupId));
			List<GroupRole> groupRoleList = DBManager.searchGroupRole(param);
			groupRole = groupRoleList.size() >= 1 ? groupRoleList.get(0) : null;
			
			if(groupRole != null) {
				
				//If user is a manager validates that there is still a manager in the group
				if(groupRole.getRole() == Role.Manager){
					SearchGroupRoleParam searchGroupRoleParam = new SearchGroupRoleParam();
					searchGroupRoleParam.setGroup(KeyFactory.stringToKey(groupId));
					searchGroupRoleParam.setRole(Role.Manager);
					List<GroupRole> managersList = DBManager.searchGroupRole(searchGroupRoleParam);
					if(managersList.size() == 1){
						response.setError("This user is the only manager of that group. The role cannot be removed until another manager is set for that group");
						return response;
					}
				}
				
				List<Role> roleList = new ArrayList<Role>();
				roleList.add(Role.Manager);
				
				List<Key> groupKeys = new ArrayList<Key>();
				groupKeys.add(groupRole.getGroup());
				
				User usr = RoleManager.getCurrentUser(sc);
				
				if(usr.getKey().equals(groupRole.getUser()) || RoleManager.userRoleMatchGroup(sc, roleList, groupKeys, true)){
					groupRole.delete();
				} else{
					throw new WebApplicationException(Response.Status.FORBIDDEN);
				}
			}
			else{
				response.setError("The user is not a member of this group");
			}
		}
		else{
			response.setError("At least a parameter is invalid");
		}

		return response;
	}

	/**
	 * GET rest/users/{id}
	 * Get the user basic informations by its ID
	 * @param id The ID of the user
	 * @return The user basic infos
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public User getUserById(@PathParam("id") String id) {

		Key key = KeyFactory.stringToKey(id);
		User user = null;

		try {
			user = DBManager.getUser(key);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	/**
	 * GET rest/users/current
	 * Get the currently authenticated user complete informations
	 * @param sc The authentication of a user
	 * @return The complete informations of the current user
	 */
	@GET
	@Path("/current")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserResponse getCurrentUserById(@Context SecurityContext sc) {
		return new UserResponse(RoleManager.getCurrentUser(sc));
	}
	
	/**
	 * GET rest/users/infos
	 * Get users complete informations by it's ID
	 * @param sc The authentication of a group manager where the user has a role or an admin
	 * @param group The group in which to get the users
	 * @param page Number of the page to show
	 * @param max Number of elements to be returned for each page
	 * @return
	 */
	@GET
	@Path("/infos")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<UserResponse> getDetailedUserList(@Context SecurityContext sc,
			@QueryParam("group") String group, 
			@QueryParam("page") int pageNumber, 
			@QueryParam("max") int resultsByPage) 	{

		List<UserResponse> returnValue = new ArrayList<UserResponse>();

		SearchUserParam searchParam = new SearchUserParam();
		searchParam.setPageNumber(pageNumber);
		searchParam.setResultsByPage(resultsByPage);

		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.Manager);

		List<Key> groupList = new ArrayList<Key>();

		if(group != null && !group.isEmpty()){
			Key groupKey = KeyFactory.stringToKey(group);
			if(DBManager.getGroup(groupKey) != null){
				searchParam.setGroup(groupKey);
				groupList.add(groupKey);
			}
			else{
				throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
			}

			if(RoleManager.userRoleMatchAllGroups(sc, roleList, groupList, true)){
				searchParam.setGroup(groupList.get(0));
				List<User> users = DBManager.searchUsers(searchParam);

				if(users != null && !users.isEmpty()){
					for(User u : users){
						returnValue.add(new UserResponse(u));
					}
				}
			}
			else{
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		else{
			for(Key gKey : RoleManager.getUserGroups(sc, roleList)){
				searchParam.setGroup(gKey);
				List<User> users = DBManager.searchUsers(searchParam);

				if(users != null && !users.isEmpty()){
					for(User u : users){
						returnValue.add(new UserResponse(u));
					}
				}
			}
		}

		return returnValue;
	}

	/**
	 * GET rest/users/{id}/infos
	 * Get a user complete informations by it's ID
	 * @param sc The authentication of a group manager where the user has a role or an admin
	 * @param id The user to get infos
	 * @return The complete informations of the user
	 */
	@GET
	@Path("{id}/infos")
	public UserResponse getFullUserById(@Context SecurityContext sc, @PathParam("id") String id){
		User user = getUserById(id);

		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.Manager);

		if(RoleManager.isUserAdmin(sc) || RoleManager.userRoleMatchGroup(sc, roleList, user.getGroups(), true)){
			return new UserResponse(user);
		}
		throw new WebApplicationException(Response.Status.FORBIDDEN);
	}

	/**
	 * PUT rest/users/{id}
	 * Change the informations of a user
	 * @param sc The authentication of the user user to edit
	 * @param id The id of the user to edit
	 * @param newUser The new informations of the user (CreateUserParam)
	 * @return Completed or error
	 */
	@PUT
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editUser(@Context SecurityContext sc, @PathParam("id") String id, CreateUserParam newUser) {

		User usr = RoleManager.getCurrentUser(sc);
		Key userKey = null;
		try{
			userKey = KeyFactory.stringToKey(id);
		}
		catch(Exception e){
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		if(usr != null && userKey != null && userKey.equals(usr.getKey())) {
			BaseResponse resp = new BaseResponse();
			try {
				if(newUser.getEmail() != null && !newUser.getEmail().isEmpty() 
						&& !newUser.getEmail().equalsIgnoreCase(usr.getEmail()))
				{
					if(DBManager.validateEmail(newUser.getEmail())){
						usr.setEmail(newUser.getEmail());
					}
					else{
						resp.setError("An account with this email is already registered");
						return resp;
					}
				}

				if(newUser.getPassword() != null && newUser.getPassword().length() >= 6 
						&& !newUser.getPassword().equals(usr.getPassword()))
				{
					usr.setPassword(newUser.getPassword());
				}

				usr.setBirthDate(newUser.getBirthDate());
				usr.setFirstName(newUser.getFirstName());
				usr.setLastName(newUser.getLastName());
				usr.setLanguage(newUser.getLanguage());

				DBManager.save(usr);
				return resp;
			}
			catch(Exception e) {
				resp.setError(e.getMessage());
				return resp;
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}

	/**
	 * DELETE rest/users/{id}
	 * Delete the user
	 * @param sc Authentication of the user to delete
	 * @param id User ID of the user to delete
	 * @return Completed or error
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse removeUser(@Context SecurityContext sc, @PathParam("id") String id) {
		BaseResponse response = new BaseResponse();
		try{
			Key key = KeyFactory.stringToKey(id);
			User user = DBManager.getUser(key);
			if(RoleManager.getCurrentUser(sc).equals(user)){
				user.delete();
			}
			else{
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		catch(Exception e)
		{
			response.setError("Error while deleting a user : " + e.getMessage());
		}

		return response;
	}

	/**
	 * POST rest/users/login
	 * Generation a session for a user to make further calls faster
	 * @param sec Authentication of the user to log in
	 * @return Session of the user
	 */
	@POST
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Session verifyUserCredentials(@Context SecurityContext sec) {
		Session response = null;
		if(sec.getUserPrincipal() != null) {
			Key userKey = KeyFactory.stringToKey(sec.getUserPrincipal().getName());
			response = SessionManager.createNewSession(userKey);
		}
		else {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
		return response;
	}


	/**
	 * POST rest/users/logout
	 * Close a user session
	 * @param sec Authentication of the user to log out
	 * @param sessionKey Session of the user to close
	 */
	@POST
	@Path("/logout")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void logout(@Context SecurityContext sec, @HeaderParam("session-id") String sessionKey) {

		if(sec.getUserPrincipal() != null) {
			// Close session
			if(!sessionKey.isEmpty()) {
				try{
					Key userKey = KeyFactory.stringToKey(sec.getUserPrincipal().getName());
					SessionManager.closeUserSession(userKey, sessionKey);
				}
				catch(Exception e){
					throw new WebApplicationException(Response.Status.NOT_FOUND);
				}
			}
		}
		else {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}

	/**
	 * POST rest/users/{email}/resetPassword
	 * Send a new password to the user address
	 * @param email Email address of the user to reset the password
	 */
	@POST
	@Path("/{email}/resetPassword")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void resetPassword(@PathParam("email") String email) {

		User user = null;
		SearchUserParam param = new SearchUserParam();
		param.setEmail(email);
		List<User> users = DBManager.searchUsers(param);
		if(users != null && !users.isEmpty()){
			user = users.get(0);
		}

		if(user != null){
			Properties props = new Properties();

			javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, null);

			String[] uid = java.util.UUID.randomUUID().toString().split("-");
			String newPassword = uid[uid.length - 1];

			//String msgBody = "Votre mot de passe a été modifié. <br/>Veuillez vous connecter avec le nouveaux mot de passe : <br/><b>" + newPassword + "</b>";
			String msgBody = "";
			BufferedReader br = null;

			try{
				String path = getClass().getClassLoader().getResource("ca/openquiz/webservice/email/resetPassword.html").getPath();

				br = new BufferedReader(new FileReader(path));

				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append('\n');
					line = br.readLine();
				}
				msgBody = sb.toString();

				msgBody = msgBody.replaceFirst("@firstName", user.getFirstName());
				msgBody = msgBody.replaceFirst("@newPassword", newPassword);
			}
			catch(Exception e){
				msgBody = "Réinitialisation du mot de passe\n\n"
						+ "Bonjour " + user.getFirstName() + ", \n\n"
						+ "Votre mot de passe a été réinitialisé\n\n"
						+ "Veuillez utiliser ce mot de passe pour vous connecter à l'avenir. Vous pouvez changer votre mot de passe en allant sur le site web de OpenQuiz.\n\n"
						+ "Nouveau mot de passe : " + newPassword;

			} finally {
				if(br != null){
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			try {
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("243950740767@project.gserviceaccount.com", "OpenQuiz"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail(), user.getFirstName() + " " + user.getLastName()));
				msg.setSubject("Nouveau mot de passe OpenQuiz");
				msg.setContent(msgBody, "text/html; charset=utf-8");

				user.setPassword(newPassword);
				DBManager.save(user);

				Transport.send(msg);
			} catch (Exception e) {
				ExceptionLogger.getLogger().warning(e.toString());
			}

			throw new WebApplicationException(Response.Status.OK);
		}
	}

}
