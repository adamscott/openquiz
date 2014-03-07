package ca.openquiz.webservice.manager.security;

import javax.ws.rs.core.Context;

import com.google.appengine.api.datastore.KeyFactory;
import com.sun.jersey.core.util.Base64;

import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Session;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.parameter.SearchUserParam;
import ca.openquiz.webservice.tools.ExceptionLogger;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

public class SecurityManager implements ContainerRequestFilter {
	
	@Context
	UriInfo uriInfo;

	@Context
	HttpServletRequest request;

	@Context
	HttpServletResponse response;

	public ContainerRequest filter(ContainerRequest request) {

		User user = null;
		try {
			if(!uriInfo.getRequestUri().getPath().contains("users/log")){
				user = validateSession();
				if(user != null) {
					request.setSecurityContext(new Authorizer(user));
					return request;
				}
			}
			user = validateUserAuthentication();
			request.setSecurityContext(new Authorizer(user));
			return request;

		} catch (Exception e) {
			request.setSecurityContext(new Authorizer(null));
		}

		return request;
	}

	private Session getSession(){
		Session session = null;
		String sessionId = request.getHeader("session-id");

		if (sessionId != null && !sessionId.isEmpty()) {
			session = DBManager.getSession(sessionId);
		}
		return session;
	}

	private User validateSession(){
		User user = null;
		Session session = getSession();
		if(session != null && session.isActive()){
			response.addHeader("session", "active");
			ExceptionLogger.getLogger().warning("Session : " + session.getSessionKey());
			if(!session.isExpired()) {
				user = DBManager.getUser(session.getUserId());
			}
		}

		return user;
	}

	private User validateUserAuthentication(){
		Date date = new Date();
		SearchUserParam p = new SearchUserParam();
		List<User> userList = null;
		User user = null;

		String header = request.getHeader("Authorization");
		if(header == null || header.isEmpty())
			return null;

		header = header.substring("Basic ".length());
		String[] creds = new String(Base64.base64Decode(header)).split(":");
		String username = creds[0];
		String password = creds[1];
		if(!username.isEmpty() && !password.isEmpty()) {
			p.setEmail(username.toLowerCase());

			userList = DBManager.searchUsers(p);
			if (userList == null || userList.isEmpty()) {
				return null;
			}
			user = userList.get(0);

			Long minutesAgo = new Long(3);
			Date dateIn_X_MinAgo = new Date(date.getTime() - minutesAgo * 60 * 1000);

			if (user.getLastLoginAttempt() == null) {
				user.setLastLoginAttempt(date);
			}

			if (user.getLoginAttempt() >= 8 && user.getLastLoginAttempt().after(dateIn_X_MinAgo)) {
				return null;
			}
			user.setLastLoginAttempt(date);
			user.setLoginAttempt(user.getLoginAttempt() + 1);
			DBManager.save(user);

			if (BCrypt.checkpw(password, user.getPassword())) {
				user.setLastLogin(date);
				user.setLoginAttempt(0);
				DBManager.save(user);

				Session userSession = getSession();
				if(userSession != null){
					SessionManager.extendSessionEndTime(userSession);
				}

				return user;
			}
		}
		return null;
	}

	/**
	 * SecurityContext used to perform authorization checks.
	 */
	public class Authorizer implements SecurityContext {

		private Principal principal = null;

		public Authorizer(final User user) {
			if (user != null) {
				ExceptionLogger.getLogger().warning("User : " + user.getEmail());
				principal = new Principal() {
					public String getName() {
						return KeyFactory.keyToString(user.getKey());
					}
				};
			}
		}

		public Principal getUserPrincipal() {
			return principal;
		}

		public boolean isSecure() {
			return "https".equals(uriInfo.getRequestUri().getScheme());
		}

		public String getAuthenticationScheme() {
			if (principal == null) {
				return null;
			}
			return SecurityContext.FORM_AUTH;
		}

		@Override
		public boolean isUserInRole(String role$groupKey) {
			return false;
		}
	}

}
