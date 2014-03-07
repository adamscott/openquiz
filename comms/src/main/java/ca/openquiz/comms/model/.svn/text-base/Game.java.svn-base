package ca.openquiz.comms.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class Game extends BaseModel {
	private static final long serialVersionUID = 1349069426363194251L;

	private String name;

	private Date gameDate;

	private String team1;

	private String team2;
	
	private int team1Score;
	
	private int team2Score;

	private String tournamentKey;

	private String questionSetKey;
	
	private boolean active;
	
	private List<String> team1Players;

	private List<String> team2Players;
	
	public Game() {
		team1Players = new ArrayList<String>(2);
		team2Players = new ArrayList<String>(2);
		active = true;
		tournamentKey = null;
	}
	
	//	Getters / Setters
	public Date getGameDate() {
		return this.gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}
	
	public List<String> getTeam1Players() {
		return team1Players;
	}

	public void setTeam1Players(List<String> team1Players) {
		this.team1Players = team1Players;
	}

	public List<String> getTeam2Players() {
		return team2Players;
	}

	public void setTeam2Players(List<String> team2Players) {
		this.team2Players = team2Players;
	}

	public void setTournamentKey(String tournamentKey) {
		this.tournamentKey = tournamentKey;
	}

	@JsonProperty("tournament")
	public String getTournamentKey() {
		return tournamentKey;
	}

	@JsonProperty("questionSet")
	public String getQuestionSetKey() {
		return questionSetKey;
	}

	public void setQuestionSetKey(String questionSet) {
		this.questionSetKey = questionSet;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean isActive) {
		this.active = isActive;
	}
	
	public String getTeam1() {
		return team1;
	}

	public void setTeam1(String team1) {
		this.team1 = team1;
	}

	public String getTeam2() {
		return team2;
	}

	public void setTeam2(String team2) {
		this.team2 = team2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return this.name + " " + dateFormat.format(gameDate);
	}
	
	@Deprecated
	@JsonProperty("teams")
	public List<String> getTeamKeys(){
		List<String> list = new ArrayList<String>();
		list.add(team1);
		list.add(team2);
		return list;
	}

	@Deprecated
	@JsonProperty("teams")
	public void setTeamKeys(List<String> teams) {
		team1 = teams.get(0);
		team2 = teams.get(1);
	}

	public int getTeam1Score() {
		return team1Score;
	}

	public void setTeam1Score(int team1Score) {
		this.team1Score = team1Score;
	}

	public int getTeam2Score() {
		return team2Score;
	}

	public void setTeam2Score(int team2Score) {
		this.team2Score = team2Score;
	}
}
