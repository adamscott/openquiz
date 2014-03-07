package ca.openquiz.webservice.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.api.datastore.Entity;

import ca.openquiz.webservice.enums.QuestionType;

@XmlRootElement
@PersistenceCapable
public class QuestionGeneral extends Question {

	@Persistent
	private String question;

	@Persistent
	private String answer;
	
	public QuestionGeneral() {

	}

	public QuestionGeneral(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Override
	public boolean isValid(){
		return super.isValid() 
				&& this.question != null && !this.question.isEmpty()
				&& this.answer != null && !this.answer.isEmpty();
	}
	
	@Override
	public QuestionType getQuestionType() {
		return QuestionType.General;
	}
	
	@Override
	public void setPropertiesFromEntity(Entity e) {
		super.setPropertiesFromEntity(e);
		this.question = (String) e.getProperty("question");
		this.answer = (String) e.getProperty("answer");
	}

}