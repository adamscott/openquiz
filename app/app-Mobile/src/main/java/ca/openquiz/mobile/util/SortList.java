package ca.openquiz.mobile.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.User;

public class SortList {
	
	public static List<String> sortGroupList(List<Group> groups)
	{
		List<String> groupsName = new ArrayList<String>();
		
		for (Group group : groups)
			groupsName.add(group.getName());
		
		Collections.sort(groupsName, new Comparator<String>() {
		    @Override
		    public int compare(String o1, String o2) {              
		        return o1.compareToIgnoreCase(o2);
		    }
		});
		
		return groupsName;
	}

	public static List<String> sortTeamList(List<Team> teams)
	{
		List<String> teamsName = new ArrayList<String>();
		
		for (Team team : teams)
			teamsName.add(team.getName());
		
		Collections.sort(teamsName, new Comparator<String>() {
		    @Override
		    public int compare(String o1, String o2) {              
		        return o1.compareToIgnoreCase(o2);
		    }
		});
		
		return teamsName;
	}
	
	public static List<String> sortUserList(List<User> users)
	{
		List<String> usersName = new ArrayList<String>();
		
		for (User user : users)
			usersName.add(user.getFirstName() + " " + user.getLastName());
		
		Collections.sort(usersName, new Comparator<String>() {
		    @Override
		    public int compare(String o1, String o2) {              
		        return o1.compareToIgnoreCase(o2);
		    }
		});
		
		return usersName;
	}
}
