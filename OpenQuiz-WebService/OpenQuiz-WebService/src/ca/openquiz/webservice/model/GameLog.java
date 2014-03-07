package ca.openquiz.webservice.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.openquiz.webservice.converter.KeyConverter;
import ca.openquiz.webservice.enums.TeamPositionEnum;
import ca.openquiz.webservice.manager.DBManager;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
@PersistenceCapable(detachable="true")
public class GameLog extends BaseModel {
	
	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key game;
	
	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key user;
	
	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key question;
	
	@Persistent
	@XmlTransient
	private double responseTime;
	
	@Persistent
	@XmlTransient
	private boolean answer;
	
	@Persistent
	@XmlTransient
	private int points;
	
	@Persistent
	@XmlTransient
	private TeamPositionEnum teamPosition;
	
	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key team;

	public GameLog() {
	}
	
	public GameLog(Key game, Key user, Key question, double responseTime, boolean answer, int points, Key team, TeamPositionEnum teamPosition) {
		this.game          	= game;
		this.user          	= user;
		this.question      	= question;
		this.responseTime  	= responseTime;
		this.answer       	= answer;
		this.points        	= points;
		this.team    	   	= team;
		this.teamPosition   = teamPosition;
	}
	
	@Override
	public void delete() {
		DBManager.delete(this.getKey());
		key = null;	
	}
	
//	Getters / Setters
	@XmlTransient
	public Key getGame() {
		return game;
	}

	public void setGame(Key game) {
		this.game = game;
	}

	@XmlTransient
	public Key getUser() {
		return user;
	}

	public void setUser(Key user) {
		this.user = user;
	}
	
	@XmlTransient
	public Key getQuestion() {
		return question;
	}

	public void setQuestion(Key question) {
		this.question = question;
	}

	@XmlElement
	public double getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(double responseTime) {
		this.responseTime = responseTime;
	}

	@XmlElement
	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	@XmlElement
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public TeamPositionEnum getTeamPosition() {
		return teamPosition;
	}

	public void setTeamPosition(TeamPositionEnum teamPossition) {
		this.teamPosition = teamPossition;
	}

	@XmlTransient
	public Key getTeam() {
		return team;
	}

	public void setTeam(Key team) {
		this.team = team;
	}

	@Override
	public boolean isValid() {
		return game != null && game.isComplete() && question != null && question.isComplete() 
				&& responseTime >= 0 && team != null && team.isComplete() && teamPosition != null;
	}
}
