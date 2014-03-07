package ca.openquiz.comms.response;

import org.codehaus.jackson.annotate.JsonProperty;

import ca.openquiz.comms.model.QuestionWrapper;

public class QuestionListResponse extends BaseResponse {

	private QuestionWrapper questions;

	@JsonProperty("questions")
	public QuestionWrapper getQuestions() {
		return questions;
	}

	public void setQuestions(QuestionWrapper questions) {
		this.questions = questions;
	}	
}
