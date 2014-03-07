package ca.openquiz.comms.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class QuestionIntru extends Question{
	private static final long serialVersionUID = 6943754709451604413L;

	public QuestionIntru() {
    }

    private String question;
    
    private List<String> choices = new ArrayList<String>();
    
    private String answer;
    
    @JsonProperty("choices")
	public List<String> getChoices() {
		return choices;
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
	
	@JsonIgnore
	public boolean isValid(){
		for(String s : this.choices) {
			if(s == null || s.isEmpty())
				return false;
		}
		return super.isValid() 
				&& this.question != null && !this.question.isEmpty()
				&& this.answer != null && !this.answer.isEmpty();
	}

	public void setChoices(List<String> choicesList) {
		choices = choicesList;		
	}    
}