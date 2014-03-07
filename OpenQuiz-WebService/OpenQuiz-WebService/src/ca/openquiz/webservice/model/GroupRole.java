package ca.openquiz.webservice.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.openquiz.webservice.converter.KeyConverter;
import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
@PersistenceCapable(detachable="true")
public class GroupRole extends BaseModel {

	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key group;
	
	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key user;
	
	@Persistent
	@XmlElement
	private Role role;

	public GroupRole() {
		
	}
	
	public GroupRole(User user, Group group, Role groupRole) {
		this.group = group.getKey();
		this.user = user.getKey();
		this.role = groupRole;
		
		user.addGroupRoles(this);
		group.addGroupRoles(this);
	}
	
	@XmlTransient
	public Key getGroup() {
		return group;
	}

	public void setGroup(Key group) {
		this.group = group;
	}

	@XmlTransient
	public Key getUser() {
		return user;
	}

	public void setUser(Key user) {
		this.user = user;
	}
	
	@XmlTransient
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role newRole) {
		role = newRole;
	}

	@Override
	public void delete() {
		
		DBManager.delete(this.getKey());
		key = null;
	}

	@Override
	public boolean isValid() {
		return group != null && group.isComplete() && user != null && user.isComplete() && role != null;
	}

}