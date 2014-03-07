package ca.openquiz.webservice.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ca.openquiz.webservice.model.User;

@XmlRootElement
public class UserListResponse extends BaseResponse 
{
	@XmlElement
	private List<User> users = new ArrayList<User>();

	public UserListResponse(){}
	
	public UserListResponse(List<User> users) {
		this.users = users;
	}
	
	public ArrayList<User> getUsers() {
		
		return new ArrayList<User>(users);
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
