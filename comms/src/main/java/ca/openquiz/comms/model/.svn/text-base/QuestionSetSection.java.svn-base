package ca.openquiz.comms.model;

import org.codehaus.jackson.annotate.JsonProperty;

import ca.openquiz.comms.enums.QuestionTarget;

public class QuestionSetSection extends BaseModel {
	private static final long serialVersionUID = 723110783673418160L;

	private String description;

	@JsonProperty("questions")
	private QuestionWrapper questionList;
	
	private int points = 10;
	
	private QuestionTarget questionTarget = QuestionTarget.Individual;
	
	public QuestionSetSection() {		
	}
	
	public void setQuestionTarget(QuestionTarget questionTarget){
		this.questionTarget = questionTarget;
	}
	
	public QuestionTarget getQuestionTarget(){
		return this.questionTarget;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPoints() {
		return points;
	}

	@JsonProperty
	public void setPoints(int points) {
		this.points = points;
	}

	public QuestionWrapper getQuestionList() {
		return questionList;
	}
	
	public void setQuestionList(QuestionWrapper questionList) {
		this.questionList = questionList;
	}
}
