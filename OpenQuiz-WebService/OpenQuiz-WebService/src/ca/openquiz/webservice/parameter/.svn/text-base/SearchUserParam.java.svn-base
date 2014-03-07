package ca.openquiz.webservice.parameter;

import com.google.appengine.api.datastore.Key;

import ca.openquiz.webservice.enums.Language;

public class SearchUserParam 
{
	private Language language = null;
	private String email = null;
	private Key group = null;
	private Key team = null;
	private int pageNumber = 1; 
	private int resultsByPage = 50;
	
	public SearchUserParam()
	{}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public void setLanguage(int language) {
		this.language = Language.values()[language];
	}

	public Key getGroup() {
		return group;
	}

	public void setGroup(Key group) {
		this.group = group;
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
