package ca.openquiz.webservice.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.appengine.api.datastore.Key;

import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.GroupRole;
import ca.openquiz.webservice.model.Team;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;
import ca.openquiz.webservice.parameter.SearchTeamParam;
import ca.openquiz.webservice.converter.*;

@XmlRootElement
public class UserResponse extends BaseResponse{
	
	@XmlElement
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key key;
	
	@XmlElement
	@XmlJavaTypeAdapter(DateConverter.class)
	private Date birthDate;

	@XmlElement
	private Date creationDate;

	@XmlElement
	private String email;

	@XmlElement
	private String firstName;

	@XmlElement
	private String lastName;

	@XmlElement
	private Language language;

	@XmlElement
	@XmlJavaTypeAdapter(DateConverter.class)
	private Date lastLogin;

	@XmlTransient
	private String password;

	@XmlElement
	private boolean isAdmin;

	@XmlElement
	private int loginAttempt;

	@XmlElement
	@XmlJavaTypeAdapter(DateConverter.class)
	private Date lastLoginAttempt;

	@XmlElement
	private List<Team> teams = new ArrayList<Team>();
	
	@XmlElement
	private List<GroupRole> groupRoles = new ArrayList<GroupRole>();
	
	public UserResponse() 
	{

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
			
			SearchTeamParam teamParam = new SearchTeamParam();
			teamParam.setUser(user.getKey());
			for(Team team: DBManager.searchTeams(teamParam))
			{
				teams.add(team);
			}
			
			SearchGroupRoleParam groupRoleParam = new SearchGroupRoleParam();
			groupRoleParam.setUser(user.getKey());
			
			for(GroupRole groupRole: DBManager.searchGroupRole(groupRoleParam))
			{
				groupRoles.add(groupRole);
			}
		}
		else {
			this.setError("user is null");
		}
	}

}
