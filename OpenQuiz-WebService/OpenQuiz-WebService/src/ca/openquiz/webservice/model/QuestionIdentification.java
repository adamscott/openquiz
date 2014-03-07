package ca.openquiz.webservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.api.datastore.Entity;

import ca.openquiz.webservice.enums.QuestionType;

@XmlRootElement
@PersistenceCapable
public class QuestionIdentification extends Question {

	public QuestionIdentification() {

	}

	@Persistent
	private List<String> statements = new ArrayList<String>();

	@Persistent
	private String answer;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<String> getStatements() {
		return statements;
	}
	
	public void setStatements(List<String> list) {
		statements = list;
	}
	
	@Override
	public QuestionType getQuestionType() {
		return QuestionType.Identification;
	}
	
	@Override
	public boolean isValid(){
		if(this.statements == null || this.statements.isEmpty()){
			return false;
		}
		
		for(String s : this.statements) {
			if(s == null || s.isEmpty())
				return false;
		}
		return super.isValid() 
				&& this.answer != null && !this.answer.isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setPropertiesFromEntity(Entity e) {
		super.setPropertiesFromEntity(e);
		this.statements = (List<String>) e.getProperty("statements");
		this.answer = (String) e.getProperty("answer");
	}

}