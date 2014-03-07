package ca.openquiz.comms.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import ca.openquiz.comms.enums.TeamPosition;

public class GameLog extends BaseModel {
	private static final long serialVersionUID = 2180897895178379679L;

	@JsonIgnore
	private String gameKey;
	@JsonIgnore
	private String userKey;
	@JsonIgnore
	private String questionKey;
	@JsonIgnore
	private String teamKey;
	@JsonIgnore
	private double responseTime;
	@JsonIgnore
	private boolean answer;
	@JsonIgnore
	private int points;
	
	private TeamPosition teamPosition = null;

	public GameLog() {
	}
	
	public GameLog(String game, String user, String question, double responseTime, boolean answer, int points, String teamKey, TeamPosition teamPosition) {
		this.gameKey          = game;       
		this.userKey          = user; 
		this.questionKey      = question;
		this.responseTime  	  = responseTime;
		this.answer           = answer;
		this.points           = points;
		this.teamKey          = teamKey;
		this.teamPosition     = teamPosition;
	}
	
//	Getters / Setters
	@JsonProperty("game")
	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String game) {
		this.gameKey = game;
	}

	@JsonProperty("user")
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String user) {
		this.userKey = user;
	}

	@JsonProperty("question")
	public String getQuestionKey() {
		return questionKey;
	}

	public void setQuestionKey(String question) {
		this.questionKey = question;
	}

	@JsonProperty("responseTime")
	public double getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(double responseTime) {
		this.responseTime = responseTime;
	}

	@JsonProperty("answer")
	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	@JsonProperty("points")
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@JsonProperty("team")
	public String getTeamKey() {
		return teamKey;
	}

	public void setTeamKey(String teamKey) {
		this.teamKey = teamKey;
	}

	public TeamPosition getTeamPosition() {
		return teamPosition;
	}

	public void setTeamPosition(TeamPosition teamPosition) {
		this.teamPosition = teamPosition;
	}
}
