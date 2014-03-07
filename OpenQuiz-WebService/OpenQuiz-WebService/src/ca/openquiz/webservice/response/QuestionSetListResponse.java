package ca.openquiz.webservice.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ca.openquiz.webservice.model.QuestionSet;

@XmlRootElement
public class QuestionSetListResponse extends BaseResponse {

	@XmlTransient
	private List<QuestionSet> questionSets = new ArrayList<QuestionSet>();

	@XmlTransient
	public List<QuestionSet> getQuestionSets() {
		return questionSets;
	}

	public void setQuestionSets(List<QuestionSet> questions) {
		this.questionSets = questions;
	}
	
	@XmlElement(name="questionSets")
	public List<StringKeyDateObj> getStringKeyDateObjList() {
		List<StringKeyDateObj> returnValue = new ArrayList<StringKeyDateObj>();
		for(QuestionSet qs : questionSets) {
			returnValue.add(new StringKeyDateObj(qs.getKey(), qs.getName(), qs.getAvailableDate()));
		}
		return returnValue;
	}
}
