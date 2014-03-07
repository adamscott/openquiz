package ca.openquiz.webservice.model;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

import ca.openquiz.webservice.enums.Degree;
import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.enums.QuestionType;
import ca.openquiz.webservice.manager.DBManager;

@PersistenceCapable
public class AvailableQuestions extends BaseModel {
	
	@Persistent
	private QuestionType type;
	
	@Persistent
	private Key category;
	
	@Persistent
	private List<Key> groups;
	
	@Persistent
	private Degree degree;
	
	@Persistent
	private Language language;
	
	@Persistent
	private int qty = 0;

	public AvailableQuestions(){
		
	}
	
	public AvailableQuestions(QuestionType type, Key category, List<Key> groups, Degree degree, Language language, int qty){
		this.type = type;
		this.category = category;
		this.groups = groups;
		this.degree = degree;
		this.language = language;
		this.qty = qty;
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



	public List<Key> getGroup() {
		return groups;
	}



	public void setGroup(List<Key> groups) {
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



	public int getQty() {
		return qty;
	}



	public void setQty(int qty) {
		this.qty = qty;
	}



	@Override
	public void delete() {
		DBManager.delete(key);
		
	}

	@Override
	public boolean isValid() {
		
		return type != null && category != null && language != null && degree != null && qty >= 0;
	}
	

}
