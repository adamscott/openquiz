package ca.openquiz.webservice.manager.security;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.GroupRole;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;
import ca.openquiz.webservice.tools.ExceptionLogger;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class RoleManager {
	
	public static Key getCurrentUserKey(SecurityContext SecurityContext) {
		if(SecurityContext != null && SecurityContext.getUserPrincipal() != null){
			Key key = KeyFactory.stringToKey(SecurityContext.getUserPrincipal().getName());
			if(key.isComplete())
				return key;
		}
		return null;
	}

	public static User getCurrentUser(SecurityContext SecurityContext){
		return DBManager.getUser(getCurrentUserKey(SecurityContext));
	}

	public static boolean isUserInRole(SecurityContext SecurityContext, List<Role> roleList) {
		if(getUserGroups(SecurityContext, roleList).size() > 0){
			return true;
		}
		return false;
	}
	
	public static boolean userRoleMatchAllGroups(SecurityContext SecurityContext, List<Role> roleList, List<Key> groupKeys, boolean mustBeLogged) {
		
		Key userKey = getCurrentUserKey(SecurityContext);
		
		if(mustBeLogged && userKey == null) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
		if(groupKeys == null || groupKeys.isEmpty()){
			return true;
		}
		if (userKey != null && groupKeys != null && !groupKeys.isEmpty()) {
			try {
				for(Key groupKey: groupKeys) {
					Role userRole = DBManager.getRole(userKey, groupKey);
					if(userRole == null || !roleList.contains(userRole)){
						throw new WebApplicationException(Response.Status.FORBIDDEN);
					}
				}
				return true;
			} catch (Exception e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				ExceptionLogger.getLogger().warning(errors.toString());
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		throw new WebApplicationException(Response.Status.FORBIDDEN);
	}
	
	public static boolean userRoleMatchGroup(SecurityContext SecurityContext, List<Role> roleList, List<Key> groupKeys, boolean mustBeLogged) {
		if(mustBeLogged && getCurrentUserKey(SecurityContext) == null) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
		if(groupKeys == null || groupKeys.isEmpty()){
			return true;
		}
		for(Key k : groupKeys){
			List<Key> keys = new ArrayList<Key>();
			keys.add(k);
			
			if(RoleManager.userRoleMatchAllGroups(SecurityContext, roleList, keys, false)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isUserAdmin(SecurityContext SecurityContext) {
		if (SecurityContext != null && SecurityContext.getUserPrincipal() != null 
				&& SecurityContext.getUserPrincipal().getName() != null 
				&& !SecurityContext.getUserPrincipal().getName().isEmpty()) {
			try{
				User u = DBManager.getUser(KeyFactory.stringToKey(SecurityContext.getUserPrincipal().getName()));
				if(u.isAdmin()){
					return true;
				}
			}
			catch(Exception e){
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		throw new WebApplicationException(Response.Status.FORBIDDEN);
	}

	public static List<Key> getUserGroups(SecurityContext SecurityContext) {
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.Manager);
		roleList.add(Role.Player);
		roleList.add(Role.QuestionManager);
		
		return getUserGroups(SecurityContext, roleList);
	}

	public static List<Key> getUserGroups(SecurityContext SecurityContext, List<Role> roles) {
		List<Key> returnValue = new ArrayList<Key>();

		Key key = getCurrentUserKey(SecurityContext);

		if(key != null){
			SearchGroupRoleParam param = new SearchGroupRoleParam();
			param.setUser(key);
			List<GroupRole> groupRoleList = DBManager.searchGroupRole(param);

			if(roles != null && !roles.isEmpty() && groupRoleList != null){
				for(GroupRole gr : groupRoleList){
					if((gr.getRole() == Role.Player && roles.contains(Role.Player)) 
							|| (gr.getRole() == Role.QuestionManager && roles.contains(Role.QuestionManager))
							|| (gr.getRole() == Role.Manager && roles.contains(Role.Manager))) {

						returnValue.add(gr.getGroup());
					}
				}
			}
		}

		return returnValue;
	}
}
