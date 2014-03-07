package ca.openquiz.webservice.parameter;

import com.google.appengine.api.datastore.Key;

public class SearchTournamentParam
{
	private Key team = null;
	private Key game = null;
	private Key group = null;
	private int pageNumber = 1; 
	private int resultsByPage = 50;
	
	private String name;
	private boolean active;
	
	public SearchTournamentParam()
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

	public Key getGame() {
		return game;
	}

	public void setGame(Key game) {
		this.game = game;
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

	public Key getGroup() {
		return group;
	}

	public void setGroup(Key group) {
		this.group = group;
	}
}
