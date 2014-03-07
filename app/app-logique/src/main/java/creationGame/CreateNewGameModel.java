package creationGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.model.Category;
import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.Template;
import ca.openquiz.comms.model.User;

 /*Until the models recover the lists form the db dynamically, uses this*/
public class CreateNewGameModel {
	
	private List<Group> Groups;
	private List<Team> Teams;
	private List<User> Users;
	private HashMap<String, ArrayList<Team>> GroupTeams;
	private HashMap<String, ArrayList<User>> TeamUsers;
	
	private List<Template> templateList;
	private Template activeTemplate;
	private List<Category> categories;
	private List<Game> gameList;
	
	public CreateNewGameModel() {
		Groups = new ArrayList<Group>();
		GroupTeams = new HashMap<String, ArrayList<Team>>();
		TeamUsers = new HashMap<String, ArrayList<User>>();
		templateList = new ArrayList<Template>();
		gameList = new ArrayList<Game>();
		categories = new ArrayList<Category>();
		Teams = new ArrayList<Team>();
		Users = new ArrayList<User>();
	}
	
	public Group getGroupByName(String groupName){
		for (Group group : Groups){
			if (groupName.equals(group.getName())){
				return group;
			}
		}
		return null;
	}
	
	public void initGroup(Group group){
		if (!Groups.contains(group)){
			Groups.add(group);
		}
		if(!GroupTeams.containsKey(group)){
			GroupTeams.put(group.getName(), new ArrayList<Team>());
		}
	}
	
	public Group loadGroupById(String groupKey){
		if (groupKey != null && groupKey != ""){
			for (Group group : Groups){
				if (group.getKey().equals(groupKey)){
					return group;
				}
			}
			//Load it from the db
			Group group = RequestsWebService.getGroup(groupKey);

			return group;
		}
		return null;
	}
	
	public Team loadTeamById(String teamKey){
		if (teamKey != null && teamKey != ""){
			for (Team team : Teams){
				if (team.getKey().equals(teamKey)){
					return team;
				}
			}
		
			//The team is not loaded locally. Get it from the db.
			Team team = RequestsWebService.getTeam(teamKey);
			if (team != null){
				addTeam(team);
			}
			return team;
		}
		return null;
	}
	
	public List<User> loadUserListByIds(List<String> playersListKeys){
		List<User> userList = new ArrayList<User>();
		for (String playerKey : playersListKeys){
			User user = loadUserById(playerKey);
			userList.add(user);
		}
		return userList;
	}
	
	public User loadUserById(String playerKey) {
		if (playerKey != null && playerKey != ""){
			for (User user : Users){
				if (user.getKey().equals(playerKey)){
					return user;
				}
			}
			
			//The user is not loaded.
			User user = RequestsWebService.getUser(playerKey);
			if (user != null){
				addUser(user);
			}
			return user;
		}
		return null;
	}
	
	public void addGame(Game game){
		if (game != null){
			gameList.add(game);
		}
	}
	
	public Game getGameByIdx(int idx){
		Game game = null;
		if (idx < gameList.size()){
			game = gameList.get(idx);
		}
		return game;
	}
	
	public void addTeamToGroup(String group, Team team){
		
		if(!GroupTeams.containsKey(group))
			GroupTeams.put(group, new ArrayList<Team>());
		
		if(!isGroupContainsTeamName(group, team.getName()))
			GroupTeams.get(group).add(team);
		
		if(!TeamUsers.containsKey(team))
			TeamUsers.put(team.getName(), new ArrayList<User>());
	}
	
	public void addUserToTeam(String team, User user){
		if(!TeamUsers.containsKey(team))
			TeamUsers.put(team, new ArrayList<User>());
		
		if(!isTeamContainsUserName(team, user.getFirstName(), user.getLastName()))
			TeamUsers.get(team).add(user);
	}
	
	public ArrayList<Team> getTeamsInGroup(String groupName){
		if(GroupTeams != null){
			return GroupTeams.get(groupName);
		}
		return null;
	}
	
	public ArrayList<User> getUsersInTeam(String teamName){
		if(TeamUsers != null){
			return TeamUsers.get(teamName);
		}
		return null;
	}
	
	public Team getTeamObjectInGroup(String groupName, String teamName){
		ArrayList<Team> teams = getTeamsInGroup(groupName);
		for (Team team : teams){
			if (team.getName().equals(teamName)){
				return team;
			}
		}
		return null;
	}
	
	public User getUserObjectInTeam(String teamName, String userName){
		List<User> users = getUsersInTeam(teamName);
		String nameCompare = "";
		for (User user : users){
			nameCompare = user.getFirstName() + " " + user.getLastName();
			if (nameCompare.equals(userName)){
				return user;
			}
		}
		return null;
	}
	
	public List<String> getUserListKeys(String groupName, String teamName,
			String teamPlayer1, String teamPlayer2, String teamPlayer3, String teamPlayer4) {
		
		List<String> userKeys = new ArrayList<String>();
		Team team = getTeamObjectInGroup(groupName, teamName);

		User player1 = getUserObjectInTeam(team.getName(), teamPlayer1);
		User player2 = getUserObjectInTeam(team.getName(), teamPlayer2);
		User player3 = getUserObjectInTeam(team.getName(), teamPlayer3);
		User player4 = getUserObjectInTeam(team.getName(), teamPlayer4);
		
		if(player1!=null){userKeys.add(player1.getKey());}
		if(player2!=null){userKeys.add(player2.getKey());}
		if(player3!=null){userKeys.add(player3.getKey());}
		if(player4!=null){userKeys.add(player4.getKey());}
		
		return userKeys;
	}
	
	public void addTemplate(Template template){
		this.templateList.add(template);
	}
	
	public List<Template> getTemplateList(){
		return this.templateList;
	}
	
	public void setTemplate(Template template, int index){
		this.templateList.set(index, template);
	}
	
	public Template getTemplate(int index){
		if(index != -1){
			return this.templateList.get(index);
		}
		return null;
	}
	
	public void setActiveTemplate(Template template){
		this.activeTemplate = template;
	}
	
	public Template getActiveTemplate(){
		return this.activeTemplate;
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	
	public List<CategoryType> getCategoriesType(){
		List<CategoryType> ct = new ArrayList<CategoryType>();
		for (Category category : categories){
			ct.add(category.getType());
		}
		return ct;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public Category getCategoryFromID(String ID){
		for (Category category : categories){
			if (category.getKey().equals(ID)){
				return category;
			}
		}
		return null;
	}
	
	public List<String> getSubCategoriesFromCategoryType(CategoryType ct){
		List<String> subCategories = new ArrayList<String>();
		
		if (ct == null){return subCategories;}
		for (Category category: categories){
			if (category.getType() == ct){
				subCategories.add(category.getName());
			}
		}
		return subCategories;
	}
	
	public String getCategoryIDFromTypeAndName(CategoryType ct, String subCategory){
		if (ct == null){return null;}
		for (Category category: categories){
			if (category.getType() == ct && category.getName().equals(subCategory)){
				return category.getKey();
			}
		}
		return null;
	}
	
	public List<String> getGroups()
	{
		List<String> groups = new ArrayList<String>();
	    
	    for (Map.Entry<String, ArrayList<Team>> entry : GroupTeams.entrySet())
	    	groups.add(entry.getKey().toString());
	    
		return groups;
	}
	
	private boolean isGroupContainsTeamName(String group, String team)
	{
		for (Team listTeam : GroupTeams.get(group))
		{
			if(listTeam.getName().equals(team))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isTeamContainsUserName(String team, String firstName, String lastName)
	{
		for (User listUser : TeamUsers.get(team))
		{
			if(listUser.getFirstName().equals(firstName) && listUser.getLastName().equals(lastName))
			{
				return true;
			}
		}
		
		return false;
	}

	public void addTeam(Team team) {
		if (team != null){
			if (!Teams.contains(team)){
				Teams.add(team);
			}
		}
	}
	
	public void addUser(User user) {
		if (user != null){
			if(!Users.contains(user)){
				Users.add(user);
			}
		}
	}
	
	public void clearTemplateList(){
		activeTemplate = null;
		templateList.clear();
	}
	
	public void clearGameList(){
		gameList.clear();
	}
	
	public void clearGroups(){
		Groups.clear();
		Teams.clear();
		GroupTeams.clear();
		TeamUsers.clear();
	}
}
