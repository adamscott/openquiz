package ca.openquiz.webservice.parameter;

import com.google.appengine.api.datastore.Key;

public class SearchGameLogParam 
{

	private Key game = null;
	private Key user = null;
	private Key question = null;
	private double responseTime = 0;
	private int correctAnswer = -1;
	private int points = 0;
	private Key team = null;
	private int pageNumber = 1; 
	private int resultsByPage = 50;
	
	public SearchGameLogParam()
	{}

	public Key getGame() {
		return game;
	}

	public void setGame(Key game) {
		this.game = game;
	}

	public Key getUser() {
		return user;
	}

	public void setUser(Key user) {
		this.user = user;
	}

	public Key getQuestion() {
		return question;
	}

	public void setQuestion(Key question) {
		this.question = question;
	}

	public double getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(double responseTime) {
		this.responseTime = responseTime;
	}

	public int isCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int answer) {
		this.correctAnswer = answer;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Key getTeam() {
		return team;
	}

	public void setTeam(Key team) {
		this.team = team;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getResultsByPage() {
		return resultsByPage;
	}

	public void setResultsByPage(int resultsByPage) {
		this.resultsByPage = resultsByPage;
	}
}
