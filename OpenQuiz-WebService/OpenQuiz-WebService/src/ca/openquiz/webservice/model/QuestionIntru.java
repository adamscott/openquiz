package ca.openquiz.webservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.appengine.api.datastore.Entity;

import ca.openquiz.webservice.enums.QuestionType;

@XmlRootElement
@PersistenceCapable
public class QuestionIntru extends Question{
 
    public QuestionIntru() {
    	
    }

    @Persistent
    @XmlElement
    private String question;
    
    @Persistent(defaultFetchGroup="true")
    @XmlElement
    private List<String> choices = new ArrayList<String>();
    
    @Persistent
    @XmlElement
    private String answer;
    
    @XmlTransient
	public List<String> getChoices() {
		return choices;
	}
    
	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

    @XmlTransient
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	@XmlTransient
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Override
	public QuestionType getQuestionType() {
		return QuestionType.Intru;
	}
	
	@Override
	public boolean isValid(){
		for(String s : this.choices) {
			if(s == null || s.isEmpty())
				return false;
		}
		return super.isValid() 
				&& this.question != null && !this.question.isEmpty()
				&& this.answer != null && !this.answer.isEmpty();
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public void setPropertiesFromEntity(Entity e) {
		super.setPropertiesFromEntity(e);
		this.question = (String) e.getProperty("question");
		this.answer = (String) e.getProperty("answer");
		this.choices = (List<String>) e.getProperty("choices");
	}
	
}