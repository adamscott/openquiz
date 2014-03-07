package structures;

import ca.openquiz.comms.model.Question;
import ca.openquiz.comms.model.QuestionWrapper;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class QuestionTest {
	
	@JsonProperty("questions")
	public QuestionWrapper questionList;
	public boolean answer;
	public int value;
	
	public QuestionTest(){
	}
	
	public QuestionWrapper getQuestionList(){
		return this.questionList;
	}
	
	@JsonIgnore
	public Question getQuestion(){
		if (questionList.getAllQuestions().size() == 1){
			return questionList.getAllQuestions().get(0);
		}
		return null;
	}
	
	public void setQuestionList(QuestionWrapper questionList){
		this.questionList = questionList;
	}
	
	public void setAnswer(boolean answer){
		this.answer = answer;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	public boolean getAnswer(){
		return this.answer;
	}

	public int getValue(){
		return this.value;
	}
}
