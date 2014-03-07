package ca.openquiz.comms.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import ca.openquiz.comms.enums.Language;
import ca.openquiz.comms.model.GroupRole;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.User;

public class UserResponse extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 8332818664680393377L;

	private String key;
	
	private Date birthDate;

	private Date creationDate;

	private String email;

	private String firstName;

	private String lastName;

	private Language language;

	private Date lastLogin;

	private String password;

	private boolean isAdmin;

	private int loginAttempt;

	private Date lastLoginAttempt;

	private List<Team> teams = new ArrayList<Team>();
	
	private List<GroupRole> groupRoles = new ArrayList<GroupRole>();
	
	public UserResponse() {
	}

	public UserResponse(User user) {
		if(user != null) {
			key = user.getKey();
			birthDate = user.getBirthDate();
			creationDate = user.getCreationDate();
			email = user.getEmail();
			firstName = user.getFirstName();
			lastName = user.getLastName();
			language = user.getLanguage();
			lastLogin = user.getLastLogin();
			password = user.getPassword();
			isAdmin = user.isAdmin();
			loginAttempt = user.getLoginAttempt();
			lastLoginAttempt = user.getLastLoginAttempt();
		}
		else {
			this.setError("user is null");
		}
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@JsonProperty("birthDate")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@JsonProperty("creationDate")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("firstName")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("lastName")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("language")
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@JsonProperty("lastLogin")
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonProperty("isAdmin")
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@JsonProperty("loginAttempt")
	public int getLoginAttempt() {
		return this.loginAttempt;
	}

	public void setLoginAttempt(int loginAttempt) {
		this.loginAttempt = loginAttempt;
	}
	
	@JsonProperty("lastLoginAttempt")
	public Date getLastLoginAttempt() {
		return this.lastLoginAttempt;
	}

	public void setLastLoginAttempt(Date lastLoginAttempt) {
		this.lastLoginAttempt = lastLoginAttempt;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<GroupRole> getGroupRoles() {
		return groupRoles;
	}

	public void setGroupRoles(List<GroupRole> groupRoles) {
		this.groupRoles = groupRoles;
	}
}
