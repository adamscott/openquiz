package ca.openquiz.comms.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class QuestionAssociation extends Question{
	private static final long serialVersionUID = -8315698894799389355L;

	public QuestionAssociation() {
    }

    private String question;
    
    private List<Choice> choices = new ArrayList<Choice>();
    
    @JsonProperty("choices")
	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	@JsonIgnore
	public boolean isValid(){
		for(Choice c : this.choices) {
			if(!c.isValid())
				return false;
		}
		return super.isValid() 
				&& this.question != null && !this.question.isEmpty()
				&& this.choices != null && !this.choices.isEmpty();
	}
    
}