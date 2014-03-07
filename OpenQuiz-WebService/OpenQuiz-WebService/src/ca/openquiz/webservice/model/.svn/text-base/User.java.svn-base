package ca.openquiz.webservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Unique;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.openquiz.webservice.converter.DateConverter;
import ca.openquiz.webservice.converter.KeyConverter;
import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.manager.security.BCrypt;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;
import ca.openquiz.webservice.parameter.SearchTeamParam;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
@PersistenceCapable(detachable="true")
public class User extends BaseModel {

	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(DateConverter.class)
	private Date birthDate;

	@Persistent
	@XmlTransient
	private Date creationDate = new Date();

	@Persistent
	@Unique
	@XmlTransient
	private String email;

	@Persistent
	@XmlElement
	private String firstName;

	@Persistent
	@XmlElement
	private String lastName;

	@Persistent
	@XmlElement
	private Language language;

	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(DateConverter.class)
	private Date lastLogin = new Date();

	@Persistent
	@XmlTransient
	private String password;

	@Persistent
	@XmlElement
	private boolean isAdmin = false;

	@Persistent
	@XmlTransient
	private int loginAttempt = 0;

	@Persistent
	@XmlJavaTypeAdapter(DateConverter.class)
	private Date lastLoginAttempt = null;

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> teams = new ArrayList<Key>();

	public User() 
	{

	}

	@XmlTransient
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@XmlTransient
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@XmlTransient
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	@XmlTransient
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlTransient
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@XmlTransient
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@XmlTransient
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	@XmlTransient
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@XmlTransient
	public int getLoginAttempt() {
		return loginAttempt;
	}

	public void setLoginAttempt(int loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	@XmlTransient
	public Date getLastLoginAttempt() {
		return lastLoginAttempt;
	}

	public void setLastLoginAttempt(Date lastLoginAttempt) {
		this.lastLoginAttempt = lastLoginAttempt;
	}

	@XmlTransient
	public List<Key> getTeams() {
		return teams;
	}

	public boolean addTeam(Team team)
	{
		if (team.getKey() == null)
			DBManager.save(team);

		if (this.key == null)
			DBManager.save(this);

		if(getGroups().contains(team.getGroup()))
		{
			if(!this.teams.contains(team.getKey())){
				this.teams.add(team.getKey());
				DBManager.save(team);
			}
			if(!team.getUsers().contains(this.getKey())){
				team.getUsers().add(this.getKey());
				DBManager.save(this);
			}
			
			return true;
		}
		return false;
	}

	public void removeTeam(Team team)
	{
		if (this.teams.contains(team.getKey()))
		{
			this.teams.remove(team.getKey());
			team.getUsers().remove(this.getKey());

			DBManager.save(team);
			DBManager.save(this);
		}
	}

	public void addGroupRoles(GroupRole groupRole)
	{
		if (groupRole.getKey() == null)
			DBManager.save(groupRole);

		if (this.key == null)
			DBManager.save(this);

		//		this.groupRoles.add(groupRole.getKey());
		groupRole.setUser(this.getKey());

		DBManager.save(groupRole);
		DBManager.save(this);
	}

	public void removeGroupRoles(GroupRole groupRole)
	{
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setUser(key);
		for(GroupRole gr: DBManager.searchGroupRole(param))
		{
			if(gr.getKey().equals(groupRole.getKey()))
			{
				Team team;
				Iterator<Key> iterator = teams.iterator();
				while(iterator.hasNext())
				{
					team = DBManager.getTeam(iterator.next());

					if(team.getGroup().equals(groupRole.getGroup()))
					{
						iterator.remove();
						team.removeUser(this);
						DBManager.save(team);
					}
				}
				groupRole.delete();
				DBManager.save(this);
			}
		}
	}

	@XmlTransient
	public List<Key> getGroups()
	{
		List<Key> groupKey = new ArrayList<Key>();
		SearchGroupRoleParam groupRoleParam = new SearchGroupRoleParam();
		groupRoleParam.setUser(this.getKey());

		for(GroupRole groupRole: DBManager.searchGroupRole(groupRoleParam))
		{
			groupKey.add(groupRole.getGroup());
		}
		return groupKey;
	}

	@Override
	public void delete()
	{
		SearchTeamParam searchParam = new SearchTeamParam();
		searchParam.setUser(this.getKey());

		for(Team t : DBManager.searchTeams(searchParam))
			t.removeUser(this);

		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setUser(this.getKey());
		for(GroupRole gr : DBManager.searchGroupRole(param))
		{
			//this.getGroupRoles().remove(gr.getKey());
			gr.delete();
		}

		DBManager.save(this);

		DBManager.delete(this.getKey());
		key = null;
	}

	@Override
	public boolean isValid() {
		if(this.key == null){
			if(!DBManager.validateEmail(email))
				return false;
		}
		
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(this.email);

		if (this.birthDate != null 
				&& this.email != null && !this.email.isEmpty() && m.matches()
				&& !this.firstName.isEmpty() && this.firstName != null
				&& !this.lastName.isEmpty() && this.lastName != null
				&& this.language != null 
				&& creationDate != null
				&& this.password != null && this.password.length() >= 6) {
			return true;
		}
		return false;
		
	}


}