package ca.openquiz.comms.model;

import org.codehaus.jackson.annotate.JsonIgnore;

public class QuestionCorrectWord extends Question{
	private static final long serialVersionUID = -6759973015769313034L;

	public QuestionCorrectWord() {
    }

    private String question;
    
    private String hint;
    
    private String answer;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
    
	@JsonIgnore
	public boolean isValid(){
		return super.isValid() 
				&& this.question != null && !this.question.isEmpty()
				&& this.hint != null && !this.hint.isEmpty()
				&& this.answer != null && !this.answer.isEmpty();
	}
    
}