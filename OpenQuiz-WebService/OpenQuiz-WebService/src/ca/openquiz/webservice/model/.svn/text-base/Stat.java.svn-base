package ca.openquiz.webservice.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class Stat implements Serializable{
	
	private int attempts = 0;
	
	private int correctAnswers = 0;
	
	private int penalties = 0;
	
	private int points = 0;
	
	public Stat(){
		
	}
	
	public Stat(List<GameLog> gameLogs){
		for(GameLog log : gameLogs){
			attempts++;
			if(log.isAnswer()){
				correctAnswers++;
			}
			else if(log.getPoints() < 0){
				penalties++;
			}
			
			points += log.getPoints();
		}
	}
	
	public Stat(int attempts, int correctAnswers, int penalties, int points){
		this.attempts = attempts;
		this.correctAnswers = correctAnswers;
		this.penalties = penalties;
		this.points = points;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public int getPenalties() {
		return penalties;
	}

	public void setPenalties(int penalties) {
		this.penalties = penalties;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
