package ca.openquiz.webservice.parameter;

import com.google.appengine.api.datastore.Key;

public class SearchGameParam
{
	private Key user = null;
	private Key team = null;
	private Key questionSet = null;
	private Key tournament = null;
	private int pageNumber = 1; 
	private int resultsByPage = 50;
	
	private String name;
	private boolean active;
	
	public SearchGameParam()
	{
		name = null;
		active = true;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Key getUser(){
		return this.user;
	}
	
	public void setUser(Key user){
		this.user = user;
	}
	
	public int getResultsByPage() {
		return resultsByPage;
	}

	public void setResultsByPage(int resultsByPage) {
		this.resultsByPage = resultsByPage;
	}

	public Key getTeam() {
		return team;
	}

	public void setTeam(Key team) {
		this.team = team;
	}

	public Key getTournament() {
		return tournament;
	}

	public void setTournament(Key tournament) {
		this.tournament = tournament;
	}

	public Key getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(Key questionSet) {
		this.questionSet = questionSet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
}
