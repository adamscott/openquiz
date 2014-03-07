package ca.openquiz.comms.model;

import org.codehaus.jackson.annotate.JsonIgnore;


public class QuestionAnagram extends Question{
	private static final long serialVersionUID = -5856286760787906918L;

	public QuestionAnagram() {
    	
    }

    private String hint;
    
    private String anagram;
    
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
	
	@JsonIgnore
	public boolean isValid(){
		return super.isValid() 
				&& this.anagram != null && !this.anagram.isEmpty()
				&& this.hint != null && !this.hint.isEmpty()
				&& this.answer != null && !this.answer.isEmpty();
	}
    
}