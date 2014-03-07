package ca.openquiz.webservice.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.api.datastore.Entity;

import ca.openquiz.webservice.enums.QuestionType;

@XmlRootElement
@PersistenceCapable
public class QuestionAnagram extends Question{
 
    public QuestionAnagram() {
    	
    }

    @Persistent
    private String hint;
    
    @Persistent
    private String anagram;
    
    @Persistent
    private String answer;

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getAnagram() {
		return anagram;
	}

	public void setAnagram(String anagram) {
		this.anagram = anagram;
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
				&& this.anagram != null && !this.anagram.isEmpty()
				&& this.hint != null && !this.hint.isEmpty()
				&& this.answer != null && !this.answer.isEmpty();
	}

	@Override
	public QuestionType getQuestionType() {
		return QuestionType.Anagram;
	}
	
	@Override
	public void setPropertiesFromEntity(Entity e) {
		super.setPropertiesFromEntity(e);
		this.anagram = (String) e.getProperty("anagram");
		this.hint = (String) e.getProperty("hint");
		this.answer = (String) e.getProperty("answer");
	}
    
}