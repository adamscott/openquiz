package openquiz.website.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Category;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.User;

public class WebServiceCache implements Serializable{
	private static final long serialVersionUID = 8469576873567201168L;
	private HashMap<String, User> cachedUsers = new HashMap<String, User>();
	private HashMap<String, Team> cachedTeams = new HashMap<String, Team>();
	private HashMap<String, Group> cachedGroup = new HashMap<String, Group>();
	
	private boolean retrievedAllcategories = false;
	private HashMap<String, Category> cachedCategories = new HashMap<String, Category>();
	
	public User getUser(String key){
		User user = cachedUsers.get(key);
		if(user != null){
			return user;
		}
		user = RequestsWebService.getUser(key);
		cachedUsers.put(key, user);
		return user;
	}
	
	public Team getTeam(String key){
		Team team = cachedTeams.get(key);
		if(team != null){
			return team;
		}
		team = RequestsWebService.getTeam(key);
		cachedTeams.put(key, team);

		return team;
	}
	
	public Group getGroup(String key){
		Group group = cachedGroup.get(key);
		if(group != null){
			return group;
		}
		group = RequestsWebService.getGroup(key);
		cachedGroup.put(key, group);

		return group;
	}

	public void uncacheObject(Object obj) {
		if(obj instanceof Team){
			cachedTeams.remove(((Team)obj).getKey());
		} else if(obj instanceof User){
			cachedUsers.remove(((User)obj).getKey());
		}else if(obj instanceof Group){
			cachedGroup.remove(((Group)obj).getKey());
		} else if(obj instanceof Category){
			cachedCategories.remove(((Category)obj).getKey());
		}
	}

	public Category getCategory(String key) {
		if(key == null)
			return null;
		Category category = cachedCategories.get(key);
		if(category != null){
			return category;
		}
		category = RequestsWebService.getCategory(key);
		if(category != null)
			cachedCategories.put(key, category);

		return category;
	}

	public Collection<Category> getCategories(String authorization) {
		if(retrievedAllcategories == false){
			retrievedAllcategories = true;
			List<Category> cats = RequestsWebService.getCategories(authorization);
			for(Category item : cats){
				cachedCategories.put(item.getKey(), item);
			}
			return cats;
		}else{
			return cachedCategories.values();
		}
	}
	
}
