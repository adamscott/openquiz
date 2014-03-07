package ca.openquiz.comms.model;

import org.codehaus.jackson.annotate.JsonIgnore;


public class Choice extends BaseModel {
	private static final long serialVersionUID = 4635742424699812907L;

	public Choice()
	{}
	
	public Choice(String choice, String answer)
	{
		this.choice = choice;
		this.answer = answer;
	}
	
	private String choice;
	
	private String answer;
	
	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@JsonIgnore
	public boolean isValid() {
		return this.choice != null && !this.choice.isEmpty()
				&& this.answer != null && !this.answer.isEmpty();
	}
	
}
