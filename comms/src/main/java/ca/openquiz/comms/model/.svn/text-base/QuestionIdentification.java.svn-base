package ca.openquiz.comms.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class QuestionIdentification extends Question {
	private static final long serialVersionUID = 8882448486790733400L;

	public QuestionIdentification() {
	}

	private List<String> statements = new ArrayList<String>();

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
	
	@JsonIgnore
	public boolean isValid(){
		for(String s : this.statements) {
			if(s == null || s.isEmpty())
				return false;
		}
		return super.isValid() 
				&& this.answer != null && !this.answer.isEmpty();
	}
	
	public void setStatements(List<String> statements){
		this.statements = statements;
	}
}