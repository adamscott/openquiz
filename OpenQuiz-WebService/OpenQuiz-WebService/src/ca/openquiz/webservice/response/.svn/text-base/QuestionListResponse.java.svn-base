package ca.openquiz.webservice.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;

import ca.openquiz.webservice.model.Question;
import ca.openquiz.webservice.model.QuestionAnagram;
import ca.openquiz.webservice.model.QuestionAssociation;
import ca.openquiz.webservice.model.QuestionGeneral;
import ca.openquiz.webservice.model.QuestionIdentification;
import ca.openquiz.webservice.model.QuestionIntru;
import ca.openquiz.webservice.model.QuestionMedia;

@XmlRootElement
public class QuestionListResponse extends BaseResponse {

	private List<Question> questions = new ArrayList<Question>();

	@XmlElementWrapper(name = "questions")
	@XmlMixed
	@XmlElementRefs({ 
			@XmlElementRef(type = QuestionGeneral.class),
			@XmlElementRef(type = QuestionIdentification.class),
			@XmlElementRef(type = QuestionAssociation.class),
			@XmlElementRef(type = QuestionAnagram.class),
			@XmlElementRef(type = QuestionIntru.class),
			@XmlElementRef(type = QuestionMedia.class)})
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}	
}
