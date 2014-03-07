package ca.openquiz.comms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import ca.openquiz.comms.enums.Language;
import ca.openquiz.comms.response.UserResponse;

public class User extends BaseModel implements Serializable {
	private static final long serialVersionUID = -3943481649608832651L;

	private Date birthDate;

	private Date creationDate = new Date();

	private String email;

	private String firstName;

	private String lastName;

	private Language language;

	private Date lastLogin = new Date();

	private String password;

	private boolean isAdmin = false;

	private int loginAttempt = 0;

	private Date lastLoginAttempt = null;

	private List<String> teams = new ArrayList<String>();

	public User() {
	}

	public User(UserResponse userResponse) {
		birthDate = userResponse.getBirthDate();
		creationDate = userResponse.getCreationDate();
		email = userResponse.getEmail();
		firstName = userResponse.getFirstName();
		lastName = userResponse.getLastName();
		language = userResponse.getLanguage();
		lastLogin = userResponse.getLastLogin();
		password = userResponse.getPassword();
		loginAttempt = userResponse.getLoginAttempt();
		lastLoginAttempt = userResponse.getLastLoginAttempt();
		for(Team team : userResponse.getTeams())
			teams.add(team.getKey());
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getLoginAttempt() {
		return this.loginAttempt;
	}

	public void setLoginAttempt(int loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	public Date getLastLoginAttempt() {
		return this.lastLoginAttempt;
	}

	public void setLastLoginAttempt(Date lastLoginAttempt) {
		this.lastLoginAttempt = lastLoginAttempt;
	}

	public List<String> getTeams() {
		return teams;
	}

	public void setTeams(List<String> teams) {
		this.teams = teams;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}