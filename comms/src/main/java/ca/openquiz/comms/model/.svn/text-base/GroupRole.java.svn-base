package ca.openquiz.comms.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import ca.openquiz.comms.enums.Role;

public class GroupRole extends BaseModel  implements Serializable{
	private static final long serialVersionUID = 7383506717180453853L;

	private String groupKey;
	
	private String userKey;
	
	private Role role;

	public GroupRole() {		
	}
	
	public GroupRole(User user, Group group, Role groupRole) {
		this.groupKey = group.getKey();
		this.userKey = user.getKey();
		this.role = groupRole;
	}

	public void setGroupKey(String group) {
		this.groupKey = group;
	}

	@JsonProperty("user")
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String user) {
		this.userKey = user;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role newRole) {
		role = newRole;
	}

	@JsonProperty("group")
	public String getGroupKey() {
		return groupKey;
	}
}