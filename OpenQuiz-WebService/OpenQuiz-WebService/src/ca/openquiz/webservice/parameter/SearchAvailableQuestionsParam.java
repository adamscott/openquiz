package ca.openquiz.webservice.parameter;

import java.util.List;

import ca.openquiz.webservice.enums.Degree;
import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.enums.QuestionType;

import com.google.appengine.api.datastore.Key;

public class SearchAvailableQuestionsParam {
	
	private boolean includePublic;

	private QuestionType type;
	
	private Key category;
	
	private List<Key> groups;
	
	private Degree degree;
	
	private Language language;
	
	public SearchAvailableQuestionsParam(){
		
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	public Key getCategory() {
		return category;
	}

	public void setCategory(Key category) {
		this.category = category;
	}

	public List<Key> getGroups() {
		return groups;
	}

	public void setGroups(List<Key> groups) {
		this.groups = groups;
	}

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public boolean isIncludePublic() {
		return includePublic;
	}

	public void setIncludePublic(boolean includePublic) {
		this.includePublic = includePublic;
	}
	
	
}
