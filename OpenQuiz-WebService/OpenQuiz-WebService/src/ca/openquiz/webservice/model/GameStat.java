package ca.openquiz.webservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import ca.openquiz.webservice.converter.JsonKeyConverter;
import ca.openquiz.webservice.enums.CategoryType;
import ca.openquiz.webservice.enums.QuestionType;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.parameter.SearchGameLogParam;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class GameStat extends BaseModel {

	@Persistent
	@JsonSerialize(using = JsonKeyConverter.class)
	private Key game;

	@Persistent
	private String gameName;

	@Persistent
	private Boolean winner;

	@Persistent
	@JsonSerialize(using = JsonKeyConverter.class)
	private Key statsFor;

	@Persistent
	@JsonSerialize(using = JsonKeyConverter.class)
	private Key team1;

	@Persistent
	@JsonSerialize(using = JsonKeyConverter.class)
	private Key team2;

	@Persistent
	private int team1Score = 0;

	@Persistent
	private int team2Score = 0;

	@Persistent(serialized = "true", defaultFetchGroup="true")
	private HashMap<CategoryType, Stat> categoryStats = new HashMap<CategoryType, Stat>();

	@Persistent(serialized = "true", defaultFetchGroup="true")
	private HashMap<QuestionType, Stat> questionTypeStats = new HashMap<QuestionType, Stat>();

	@Persistent(serialized = "true", defaultFetchGroup="true")
	private Stat overallStats;

	@Persistent
	private int possiblePoints;

	@Persistent
	private int possibleAttempts;

	@Persistent
	private Date gameDate = new Date();

	@Persistent
	@JsonSerialize(using = JsonKeyConverter.class)
	private Key poster;
	
	@Persistent
	@XmlTransient
	private List<Key> teams = new ArrayList<Key>();

	public GameStat(){

	}

	public GameStat(Key gameKey, Key statsFor, int possiblePoints, int possibleAttempts, User poster){
		this.statsFor = statsFor;

		Game g = DBManager.getGame(gameKey);

		this.game = g.getKey();
		this.gameName = g.getName();
		this.team1 = g.getTeam1();
		this.team1Score = g.getTeam1Score();
		this.team2 = g.getTeam2();
		this.team2Score = g.getTeam2Score();

		this.possiblePoints = possiblePoints;
		this.possibleAttempts = possibleAttempts;
		this.poster = poster.getKey();
		this.gameDate = new Date();

		// Find who won the game
		if((g.getTeam1Players().contains(statsFor) || g.getTeam1().equals(statsFor)) 
				&& g.getTeam1Score() != g.getTeam2Score()){
			this.winner = g.getTeam1Score() > g.getTeam2Score();
		}
		else if((g.getTeam2Players().contains(statsFor) || g.getTeam2().equals(statsFor)) 
				&& g.getTeam1Score() != g.getTeam2Score()){
			this.winner = g.getTeam1Score() < g.getTeam2Score();
		}
		else{
			this.winner = null;
		}

		SearchGameLogParam param = new SearchGameLogParam();
		param.setGame(gameKey);
		param.setPageNumber(0);
		param.setResultsByPage(0);
		
		//Kinda ugly but eh..
		if(statsFor.getKind().equals(team1.getKind())){
			param.setTeam(statsFor);
		}else{
			param.setUser(statsFor);
		}
		
		List<GameLog> gameLogs = DBManager.searchGameLog(param);

		if((gameLogs != null && !gameLogs.isEmpty())){

			this.overallStats = new Stat(gameLogs);

			HashMap<QuestionType, List<GameLog>> questionLogs = getQuestionLogsMap();
			HashMap<CategoryType, List<GameLog>> categoryLogs = getCategoryLogsMap();

			for(GameLog log : gameLogs){
				Question q = DBManager.getQuestion(log.getQuestion());

				if(q != null){
					questionLogs.get(q.getQuestionType()).add(log);
					categoryLogs.get(DBManager.getCategory(q.getCategory()).getType()).add(log);
				}
			}

			for(QuestionType type : QuestionType.values()){
				List<GameLog> logs = questionLogs.get(type);
				if(logs != null && !logs.isEmpty()){
					questionTypeStats.put(type, new Stat(logs));
				}
			}

			for(CategoryType type : CategoryType.values()){
				List<GameLog> logs = categoryLogs.get(type);
				if(logs != null && !logs.isEmpty()){
					categoryStats.put(type, new Stat(logs));
				}
			}
		}
		else{
			this.overallStats = new Stat();
		}
	}

	
	private HashMap<CategoryType, List<GameLog>> getCategoryLogsMap(){

		HashMap<CategoryType, List<GameLog>> returnValue = new HashMap<CategoryType, List<GameLog>>();

		for(CategoryType cat : CategoryType.values()){
			returnValue.put(cat, new ArrayList<GameLog>());
		}

		return returnValue;
	}

	private HashMap<QuestionType, List<GameLog>> getQuestionLogsMap(){

		HashMap<QuestionType, List<GameLog>> returnValue = new HashMap<QuestionType, List<GameLog>>();

		for(QuestionType cat : QuestionType.values()){
			returnValue.put(cat, new ArrayList<GameLog>());
		}

		return returnValue;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	public Key getGame() {
		return game;
	}

	public void setGame(Key game) {
		this.game = game;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Key getStatsFor() {
		return statsFor;
	}

	public void setStatsFor(Key statsFor) {
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

	public int getPossibleAttempts() {
		return possibleAttempts;
	}

	public void setPossibleAttempts(int possibleAttempts) {
		this.possibleAttempts = possibleAttempts;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

	public Key getTeam1() {
		return team1;
	}

	public void setTeam1(Key team1) {
		this.team1 = team1;
	}

	public Key getTeam2() {
		return team2;
	}

	public void setTeam2(Key team2) {
		this.team2 = team2;
	}

	@XmlTransient
	public int getTeam1Score() {
		return team1Score;
	}

	public void setTeam1Score(int team1Score) {
		this.team1Score = team1Score;
	}

	@XmlTransient
	public int getTeam2Score() {
		return team2Score;
	}

	public void setTeam2Score(int team2Score) {
		this.team2Score = team2Score;
	}

	@XmlTransient
	public Key getPoster() {
		return poster;
	}

	public void setPoster(Key poster) {
		this.poster = poster;
	}

	@Override
	public void delete() {

		DBManager.delete(this.key);

	}

	@Override
	public boolean isValid() {
		teams.clear();
		teams.add(team1);
		teams.add(team2);
		
		return game != null && statsFor != null && gameDate != null && gameName != null
				&& possiblePoints >= 0 && possibleAttempts >= 0 && overallStats != null
				&& team1 != null && team2 != null;
	}
}
