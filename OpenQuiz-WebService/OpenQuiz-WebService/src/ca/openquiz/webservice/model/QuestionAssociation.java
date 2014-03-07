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
public class QuestionAssociation extends Question{
 
    public QuestionAssociation() {
    	
    }

    @Persistent
    private String question;
    
    @Persistent(defaultFetchGroup="true")
    private List<Choice> choices = new ArrayList<Choice>();
    
	public List<Choice> getChoices() {
		return choices;
	}
    
    public void setChoices(List<Choice> choices) {
    	for(Choice c : this.choices){
    		c.delete();
    	}
    	this.choices.clear();
		this.choices.addAll(choices);
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	@Override
	public boolean isValid(){
		if(this.choices == null || this.choices.isEmpty()){
			return false;
		}
		
		for(Choice c : this.choices) {
			if(!c.isValid())
				return false;
		}
		
		return super.isValid() 
				&& this.question != null && !this.question.isEmpty()
				&& this.choices != null && !this.choices.isEmpty();
	}
	
	@Override
	public QuestionType getQuestionType() {
		return QuestionType.Association;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setPropertiesFromEntity(Entity e) {
		super.setPropertiesFromEntity(e);
		this.question = (String) e.getProperty("question");
		this.choices = (List<Choice>) e.getProperty("choices");
	}
    
}