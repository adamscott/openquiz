package ca.openquiz.comms.response;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import ca.openquiz.comms.model.QuestionSet;

public class QuestionSetListResponse extends BaseResponse {

	private List<QuestionSet> questionSets = new ArrayList<QuestionSet>();

	@JsonProperty("questionSets")
	public List<QuestionSet> getQuestionSets() {
		return questionSets;
	}

	public void setQuestionSets(List<QuestionSet> questions) {
		this.questionSets = questions;
	}
}
