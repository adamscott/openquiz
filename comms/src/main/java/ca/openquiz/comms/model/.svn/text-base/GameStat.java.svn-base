package ca.openquiz.comms.model;

import java.util.Date;
import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonIgnore;

import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.enums.QuestionType;

public class GameStat extends BaseModel {
	private static final long serialVersionUID = 4893675510571164915L;

	private String game;
	
	private String gameName;
	
	private Boolean winner;
	
	private String statsFor;
	
	private String poster;
	
	private int team1Score;
	
	private int team2Score;
	
	private String team1;

	private String team2;
	
	private HashMap<CategoryType, Stat> categoryStats = new HashMap<CategoryType, Stat>();
	
	private HashMap<QuestionType, Stat> questionTypeStats = new HashMap<QuestionType, Stat>();
	
	private Stat overallStats;
	
	private int possiblePoints;
	
	private int possibleAttempts;
	
	private Date gameDate = new Date();

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

	@JsonIgnore
	public boolean isValid() {
		return game != null && statsFor != null && team1 != null && team2 != null;
	}
	
	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getStatsFor() {
		return statsFor;
	}

	public void setStatsFor(String statsFor) {
		this.statsFor = statsFor;
	}

	public HashMap<CategoryType, Stat> getCategoryStats() {
		return categoryStats;
	}

	public void setCategoryStats(HashMap<CategoryType, Stat> categoryStats) {
		this.categoryStats = categoryStats;
	}

	public HashMap<QuestionType, Stat> getQuestionTypeStats() {
		return questionTypeStats;
	}

	public void setQuestionTypeStats(HashMap<QuestionType, Stat> questionTypeStats) {
		this.questionTypeStats = questionTypeStats;
	}

	public Stat getOverallStats() {
		return overallStats;
	}

	public void setOverallStats(Stat overallStats) {
		this.overallStats = overallStats;
	}

	public int getPossiblePoints() {
		return possiblePoints;
	}

	public void setPossiblePoints(int possiblePoints) {
		this.possiblePoints = possiblePoints;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	public int getPossibleAttempts() {
		return possibleAttempts;
	}

	public void setPossibleAttempts(int possibleAttempts) {
		this.possibleAttempts = possibleAttempts;
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

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	
}
