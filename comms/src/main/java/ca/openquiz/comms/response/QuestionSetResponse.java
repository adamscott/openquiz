package ca.openquiz.comms.response;

import ca.openquiz.comms.model.QuestionSet;

public class QuestionSetResponse extends BaseResponse 
{
	private QuestionSet questionSet;

	public QuestionSet getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(QuestionSet key) {
		this.questionSet = key;
	}
}
