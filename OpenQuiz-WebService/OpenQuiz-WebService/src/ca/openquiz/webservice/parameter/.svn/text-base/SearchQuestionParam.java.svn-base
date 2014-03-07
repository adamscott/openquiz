package ca.openquiz.webservice.parameter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;

import ca.openquiz.webservice.enums.Degree;
import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.enums.QuestionType;

public class SearchQuestionParam {
	QuestionType type = QuestionType.General;
	Language language = null;
	Degree degree = null;
	List<Key> groups = new ArrayList<Key>();
	Key author = null;
	Key category = null;
	boolean isReported = false;
	int resultsByPage = 50;
	int pageNumber = 1;
	boolean includePublic = true;
	int offset = 0;
	Date availableDate = new Date();
	
	public SearchQuestionParam(){
		
	}
	public QuestionType getType() {
		return type;
	}
	public void setType(QuestionType type) {
		this.type = type;
	}
	public List<Key> getGroups() {
		return groups;
	}
	public void setGroups(List<Key> group) {
		this.groups = group;
	}
	public Key getAuthor() {
		return author;
	}
	public void setAuthor(Key author) {
		this.author = author;
	}	
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public Key getCategory() {
		return category;
	}
	public boolean isIncludePublic() {
		return includePublic;
	}
	public void setIncludePublic(boolean includePublic) {
		this.includePublic = includePublic;	
	}
	public void setCategory(Key category) {
		this.category = category;
	}
	public int getResultsByPage() {
		return resultsByPage;
	}
	public void setResultsByPage(int resultsByPage) {
		this.resultsByPage = resultsByPage;
	}
	public boolean isReported() {
		return isReported;
	}
	public void setReported(boolean isReported) {
		this.isReported = isReported;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Degree getDegree() {
		return degree;
	}
	public void setDegree(Degree degree) {
		this.degree = degree;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public Date getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}
}
