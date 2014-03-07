package ca.openquiz.webservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.appengine.api.datastore.Key;

import ca.openquiz.webservice.enums.QuestionTarget;
import ca.openquiz.webservice.manager.DBManager;

@XmlRootElement
@PersistenceCapable
public class QuestionSetSection extends BaseModel {

	@Persistent
	@XmlElement
	private String description;

	@Persistent
	@XmlTransient
	private List<Key> questionList = new ArrayList<Key>();

	@Persistent
	@XmlElement
	private int points = 10;
	
	@Persistent
	@XmlElement
	private QuestionTarget questionTarget = QuestionTarget.Individual;

	public QuestionSetSection() {

	}

	@XmlMixed
	@XmlElementWrapper(name= "questions")
	@XmlElementRefs({ @XmlElementRef(type = QuestionGeneral.class),
		@XmlElementRef(type = QuestionIdentification.class),
		@XmlElementRef(type = QuestionAssociation.class),
		@XmlElementRef(type = QuestionAnagram.class),
		@XmlElementRef(type = QuestionIntru.class),
		@XmlElementRef(type = QuestionMedia.class)})
	public List<Question> getQuestionListObjects() {
		if(!questionList.isEmpty()){
			List<Question> returnValue = new ArrayList<Question>();

			for(Key k : questionList){
				Question q = DBManager.getQuestion(k);
				if(q != null)
					returnValue.add(q);
			}

			return returnValue;
		}
		return null;
	}
	
	@XmlTransient
	public List<Key> getQuestionList() {
		return this.questionList;
	}

	@XmlTransient
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlTransient
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void addQuestion(Question q) {

		if (q.getKey() == null) {
			DBManager.save(q);
		}

		questionList.add(q.getKey());
	}
	
	@XmlTransient
	public QuestionTarget getQuestionTarget() {
		return questionTarget;
	}

	public void setQuestionTarget(QuestionTarget questionTarget) {
		this.questionTarget = questionTarget;
	}

	@Override
	public void delete() {
		DBManager.delete(this.getKey());
		this.questionList.clear();
		this.key = null;

	}

	@Override
	public boolean isValid() {
		return points > 0 && questionList != null && !questionList.isEmpty();
	}

}
