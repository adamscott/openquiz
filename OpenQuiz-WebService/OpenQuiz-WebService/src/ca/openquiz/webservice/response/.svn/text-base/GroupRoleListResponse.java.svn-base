package ca.openquiz.webservice.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ca.openquiz.webservice.model.GroupRole;

@XmlRootElement
public class GroupRoleListResponse extends BaseResponse 
{
	@XmlElement
	private List<GroupRole> groupRoles = new ArrayList<GroupRole>();
	
	public List<GroupRole> getUsers() {
		return groupRoles;
	}

	public void setUsers(List<GroupRole> groupRoles) {
		this.groupRoles = groupRoles;
	}
}
