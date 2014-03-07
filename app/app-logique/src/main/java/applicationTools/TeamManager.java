package applicationTools;

import java.util.ArrayList;
import java.util.List;

import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.User;

public class TeamManager {
	
	private static Team LeftTeam;
	private static Team RightTeam;
	private static List<User> LeftTeamPlayers;
	private static List<User> RightTeamPlayers;

	private static TeamManager INSTANCE = null;
	
	public static TeamManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new TeamManager();
		}
			return INSTANCE;
	}
	
	private TeamManager() {
		LeftTeam = new Team();
		RightTeam = new Team();
		LeftTeamPlayers = new ArrayList<User>();
		RightTeamPlayers = new ArrayList<User>();
	}
	
	public void addPlayerToLeftTeam(User user){
		LeftTeamPlayers.add(user);
	}
	
	public void addPlayerToRightTeam(User user){
		RightTeamPlayers.add(user);
	}
	
	public void resetTeams() {
		LeftTeamPlayers.clear();
		RightTeamPlayers.clear();
		LeftTeam = new Team();
		RightTeam = new Team();
	}
	
	public void setRightTeam(Team team) {
		RightTeam = team;
	}
	
	public void setLeftTeam(Team team) {
		LeftTeam = team;
	}
	
	public List<User> getLeftTeamPlayers(){
		return LeftTeamPlayers;
	}
	
	public List<User> getRightTeamPlayers(){
		return RightTeamPlayers;
	}
	
	public void setRightTeamPlayers(List<User> users){
		RightTeamPlayers = users;
	}
	
	public void setLeftTeamPlayers(List<User> users){
		LeftTeamPlayers = users;
	}
	
	public Team getRightTeam() {
		return RightTeam;
	}
	
	public Team getLeftTeam() {
		return LeftTeam;
	}
	
	public List<String> getLeftTeamNames(){
		List<String> playersName = new ArrayList<String>();
		for (User user : LeftTeamPlayers){
			if(user!=null)
				playersName.add((user.getFirstName() + " " + user.getLastName()));
		}
		return playersName;
	}
	
	public List<String> getRightTeamNames(){
		List<String> playersName = new ArrayList<String>();
		for (User user : RightTeamPlayers){
			if(user!=null)
				playersName.add((user.getFirstName() + " " + user.getLastName()));
		}
		return playersName;
	}
	
	public User getUser(String playerName, String teamSide){
		User returnUser = new User();
		if (teamSide == "Left"){
			for (User user : LeftTeamPlayers){
				if (user!=null && playerName.equals(user.getFirstName() + " " + user.getLastName())){
					returnUser = user;
				}
			}
		}
		else if (teamSide == "Right"){
			for (User user : RightTeamPlayers){
				if (user!=null && playerName.equals(user.getFirstName() + " " + user.getLastName())){
					returnUser = user;
				}
			}
		}
		return returnUser;
	}

}
