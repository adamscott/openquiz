package ca.openquiz.comms.model;

import org.codehaus.jackson.annotate.JsonIgnore;


public class QuestionGeneral extends Question {
	private static final long serialVersionUID = 9104002360860078900L;

	private String question;

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
	
	@JsonIgnore
	public boolean isValid(){
		return super.isValid() 
				&& this.question != null && !this.question.isEmpty()
				&& this.answer != null && !this.answer.isEmpty();
	}

}